package com.example.nguyenngocsonc.musicsimplekotlin.builder

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.support.annotation.RequiresApi

/**
 * Created by nguyen.ngoc.sonc on 1/10/18.
 */
class BlurBuilder {
    private val BITMAP_SCALE = 0.4f
    private val BLUR_RADIUS = 8.5f

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun blur(context: Context, image: Bitmap): Bitmap {
        var width = Math.round(image.width * BITMAP_SCALE)
        var height = Math.round(image.height * BITMAP_SCALE)

        var inputBitmap = Bitmap.createScaledBitmap(image, width, height, false)
        var outputBitmap = Bitmap.createBitmap(inputBitmap)

        var rs = RenderScript.create(context)
        var theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        var tmpIn = Allocation.createFromBitmap(rs, inputBitmap)
        var tmpOut = Allocation.createFromBitmap(rs, outputBitmap)
        theIntrinsic.setRadius(BLUR_RADIUS)
        theIntrinsic.setInput(tmpIn)
        theIntrinsic.forEach(tmpOut)
        tmpOut.copyTo(outputBitmap)

        return outputBitmap
    }
}