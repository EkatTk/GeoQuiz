package com.bignerdranch.android.geomain

import android.support.annotation.StringRes

data class Question(@StringRes val textResId:
                    Int, val answer: Boolean)
