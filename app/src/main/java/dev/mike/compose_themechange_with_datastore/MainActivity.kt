package dev.mike.compose_themechange_with_datastore

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mike.compose_themechange_with_datastore.ui.theme.ComposeThemeChangeWithDataStoreTheme

class MainActivity : AppCompatActivity() {

    val viewModel: AppViewModel by viewModels {
        AppViewModelFactory((application as ThemeChangeApplication).dataStoreRepo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val theme = viewModel.theme.collectAsState(initial = AppThemes.MODE_AUTO).value

            val useDarkTheme = theme?.let {
                when (it) {
                    AppThemes.MODE_AUTO -> isSystemInDarkTheme()
                    AppThemes.MODE_LIGHT -> false
                    AppThemes.MODE_DARK -> true
                }
            } ?: isSystemInDarkTheme()

            ComposeThemeChangeWithDataStoreTheme(darkTheme = useDarkTheme) {

                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
                    Column() {
                        Spacer(modifier = Modifier.height(30.dp))
                        if (theme != null) {
                            Text(text = "Current Theme Setting is ${theme.name}")
                            Spacer(modifier = Modifier.height(30.dp))

                        }
                        ChangeThemeButtons {

                            viewModel.changeTheme(it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeThemeChangeWithDataStoreTheme(darkTheme = false) {
        Greeting("Android")
    }
}

@Composable
fun ChangeThemeButtons(
    onClick: (AppThemes) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(30.dp)) {

        Button(onClick = { onClick(AppThemes.MODE_LIGHT) }) {

            Text(text = "Change to Light Mode")
        }
        Button(onClick = { onClick(AppThemes.MODE_DARK) }) {

            Text(text = "Change to Dark Mode")
        }

        Button(onClick = { onClick(AppThemes.MODE_AUTO) }) {

            Text(text = "Change to Auto Mode")
        }

        Text(text = "is the system in dark mode? -> ${isSystemInDarkTheme()}")
    }
}
