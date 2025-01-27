package com.morfly.imageviewer.imageloader

enum class ImageLoadingPolicy(val index: Int) {
    NO_MEMORY_CACHE(1 shl 0),
    OFFLINE(1 shl 1);

    operator fun invoke() = index
}

infix fun ImageLoadingPolicy.isAppliedTo(policy: Int): Boolean {
    return (policy and index) == index
}

infix fun ImageLoadingPolicy.isNotAppliedTo(policy: Int) = isAppliedTo(policy).not()