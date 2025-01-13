package org.devvikram.quizo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform