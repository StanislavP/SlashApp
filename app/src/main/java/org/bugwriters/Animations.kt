package org.bugwriters

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

fun exitLeft(scope: AnimatedContentTransitionScope<NavBackStackEntry>): ExitTransition {
    return scope.slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(700)
    )

}


fun exitRight(scope: AnimatedContentTransitionScope<NavBackStackEntry>): ExitTransition {

    return scope.slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(700)
    )

}

fun exitUp(scope: AnimatedContentTransitionScope<NavBackStackEntry>): ExitTransition {

    return scope.slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Up,
        animationSpec = tween(700)
    )

}

fun exitDown(scope: AnimatedContentTransitionScope<NavBackStackEntry>): ExitTransition {

    return scope.slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Down,
        animationSpec = tween(700)
    )

}

fun enterDown(scope: AnimatedContentTransitionScope<NavBackStackEntry>): EnterTransition {

    return scope.slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Down,
        animationSpec = tween(700)
    )

}

fun enterUp(scope: AnimatedContentTransitionScope<NavBackStackEntry>): EnterTransition {

    return scope.slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Down,
        animationSpec = tween(700)
    )

}

fun enterLeft(scope: AnimatedContentTransitionScope<NavBackStackEntry>): EnterTransition {

    return scope.slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Down,
        animationSpec = tween(700)
    )

}

fun enterRight(scope: AnimatedContentTransitionScope<NavBackStackEntry>): EnterTransition {

    return scope.slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Down,
        animationSpec = tween(700)
    )

}