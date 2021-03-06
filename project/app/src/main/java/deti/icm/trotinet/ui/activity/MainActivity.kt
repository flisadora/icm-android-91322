package deti.icm.trotinet.ui.activity

import android.app.KeyguardManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CancellationSignal
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import deti.icm.trotinet.R
import deti.icm.trotinet.database.AppDatabase
import deti.icm.trotinet.model.Ride
import deti.icm.trotinet.model.User
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {

    private var cancellationSignal: CancellationSignal? = null

    private val authenticationCallback: BiometricPrompt.AuthenticationCallback
    get() =
        @RequiresApi(Build.VERSION_CODES.P)
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                notifyUser("Authentication error: $errString")
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                notifyUser("Successful authentication!")
                startActivity(Intent(this@MainActivity, MapsActivity::class.java))
            }
        }

    private fun notifyUser(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repositoryInit()
        checkBiometricSupport()

        val auth_button = findViewById<Button>(R.id.auth_button)
        auth_button.setOnClickListener {
            val biometricPrompt = BiometricPrompt.Builder(this)
                .setTitle("Title")
                .setSubtitle("Authentication is required")
                .setDescription("This app uses fingerprint protection to keep you data secure")
                .setNegativeButton("Cancel", this.mainExecutor, DialogInterface.OnClickListener {
                    dialog, which -> notifyUser("Authentication cancelled")
                }).build()

            biometricPrompt.authenticate(getCancellationSignal(), mainExecutor, authenticationCallback)
        }
    }

    private fun getCancellationSignal(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            notifyUser("Authentication cancelled by the user")
        }
        return cancellationSignal as CancellationSignal
    }

    private fun checkBiometricSupport(): Boolean {

        val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        if (!keyguardManager.isKeyguardSecure) {
            notifyUser("Fingerprint authentication has not been enabled in settings")
            return false
        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED) {
            notifyUser("Fingerprint authentication permission is no enabled")
            return false
        }

        return if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            true
        } else true
    }

    private fun repositoryInit() {
        val repository = AppDatabase.instance(this).appDao()
        if(repository.getAllUsers().isEmpty()) {
            repository.addUser(User(0L, "Charles Xavier", "xmen@ymail.com", 14.7))
            val user = repository.getAllUsers()[0]
            Log.i("REPO:", "${repository.getAllRides().isEmpty()}")
            Log.i("REPO:", "${repository.getAllRides()}")
            if (repository.getAllRides().isEmpty()) {
                repository.addRide(Ride(0L, user.uid, 2500.0, 3.25,"Esta????o de Aveiro", "Universidade de Aveiro, 3810-193 Aveiro", LocalDateTime.now()))
                repository.addRide(Ride(0L, user.uid, 2100.0, 2.5,"Universidade de Aveiro, 3810-193 Aveiro", "Forum Aveiro, 3810-064 Aveiro", LocalDateTime.now()))
                repository.addRide(Ride(0L, user.uid,1100.0, 2.25,"Forum Aveiro, 3810-064 Aveiro", "Salinas de Aveiro, 3800-180 Aveiro", LocalDateTime.now()))
                repository.addRide(Ride(0L, user.uid,3400.0, 4.75,"Salinas de Aveiro, 3800-180 Aveiro", "Loja do Cidad??o de Aveiro, 3800-990 Aveiro", LocalDateTime.now()))
                Log.i("REPO:", "${repository.loadUserRides(user.uid)}")
            }
        }
    }

}