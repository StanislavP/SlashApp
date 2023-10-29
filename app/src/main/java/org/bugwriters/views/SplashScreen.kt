package org.bugwriters.views

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import org.bugwriters.Config
import org.bugwriters.R
import org.bugwriters.Screens
import org.bugwriters.connection.API
import org.bugwriters.connection.createRetrofitService
import org.bugwriters.connection.executeRequest
import org.bugwriters.connection.models.bodies.Roles

@Composable
fun SplashScreen(
    navController: NavController,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val scaleInitial = remember {
            Animatable(0.0f)
        }

        LaunchedEffect(key1 = true) {

            scaleInitial.animateTo(targetValue = 1f, animationSpec = tween(800, easing = {
                OvershootInterpolator(4f).getInterpolation(it)
            }))

            val service = createRetrofitService(API::class.java)
            var screen by mutableStateOf(Screens.login)


            if (Config.cookie.isNotBlank()) {
                withContext(Dispatchers.IO) {
                    async {
                        executeRequest(true) {
                            service.isCookieValid()
                        }.onSuccess {
                            screen =
                                if (Config.role != null && Config.role == Roles.ROLE_CLIENT.name) Screens.main_screen_client
                                else if (Config.role != null && Config.role == Roles.ROLE_BUSINESS.name) Screens.main_screen_business
                                else Screens.login
                        }.onFailure {
                            Config.clear()
                        }.onServerError {
                            Config.clear()
                        }
                    }.await()
                }
            }
            withContext(Dispatchers.Main) {
                navController.navigate(screen) {
                    launchSingleTop = true
                    popUpTo(screen)
                }
            }


        }

        Image(
            painter = painterResource(id = R.drawable.slash),
            contentDescription = null,
            alignment = Alignment.Center,

            modifier = Modifier
                .fillMaxSize()
                .padding(120.dp)
                .scale(scaleInitial.value)
        )
    }
}
