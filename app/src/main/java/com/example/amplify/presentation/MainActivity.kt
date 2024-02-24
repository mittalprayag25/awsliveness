package com.example.amplify.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.amplifyframework.ui.liveness.ui.FaceLivenessDetector
import com.example.amplify.presentation.viewmodel.MainViewModel
import com.example.amplify.ui.theme.AmplifyTheme
import com.example.amplify.utility.Permission
import com.example.amplify.utility.PermissionManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel:MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                    Column {
                        Button(
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.CenterHorizontally),
                            onClick = {
                            // hit the api
                            viewModel.fetchData()
                        }) {
                            Text(text = "Click")
                        }
                    }

                    if(viewState.data.isNotEmpty()){
                        // Todo: you will get your data here once get it from api
                        Log.d("data :::",viewState.data.size.toString())
                    }

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
