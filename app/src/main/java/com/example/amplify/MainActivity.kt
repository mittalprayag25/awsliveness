package com.example.amplify

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amplifyframework.api.ApiException
import com.amplifyframework.api.rest.RestOptions
import com.amplifyframework.core.Amplify
import com.amplifyframework.ui.liveness.ui.FaceLivenessDetector
import com.example.amplify.ui.theme.AmplifyTheme
import com.raq.motori.android.customerapp.utility.permission.Permission
import com.raq.motori.android.customerapp.utility.permission.PermissionManager

class MainActivity : ComponentActivity() {
    private lateinit var viewModel:MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        requestCameraPermission{
            viewModel.getCameraAccess()
        }
        setContent {
            AmplifyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val viewState by viewModel.viewState.collectAsState()
                    if(viewState.isCameraAccessGranted)
                    FaceLivenessDetector(
                        sessionId = "0f959dbb-37cc-45d8-a08d-dc42cce85fa8",
                        region = "us-east-1",
                        onComplete = {
                            Log.i("MyApp", "Face Liveness flow is complete")
                            // The Face Liveness flow is complete and the session
                            // results are ready. Use your backend to retrieve the
                            // results for the Face Liveness session.
                        },
                        onError = { error ->
                            Log.e("MyApp", "Error during Face Liveness flow", error.throwable)
                            // An error occurred during the Face Liveness flow, such as
                            // time out or missing the required permissions.
                        }
                    )
                }
            }
        }
    }



    private fun requestCameraPermission(onGranted:()->Unit){
        PermissionManager.from(this)
            // Check one permission at a time
            .request(Permission.Camera)
            .rationale("We need permission to see your beautiful face")
            .checkPermission { granted ->
                if (granted) {
                    onGranted()
                } else {
                    // failure
                }
            }
    }
}
