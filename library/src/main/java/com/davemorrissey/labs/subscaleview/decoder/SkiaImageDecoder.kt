package com.davemorrissey.labs.subscaleview.decoder

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.davemorrissey.labs.subscaleview.internal.DECODER_NULL_MESSAGE
import com.davemorrissey.labs.subscaleview.internal.URI_PATH_ASSET
import com.davemorrissey.labs.subscaleview.internal.URI_SCHEME_FILE
import com.davemorrissey.labs.subscaleview.internal.URI_SCHEME_RES
import com.davemorrissey.labs.subscaleview.internal.URI_SCHEME_ZIP
import java.util.zip.ZipFile

/**
 * Default implementation of [com.davemorrissey.labs.subscaleview.decoder.ImageDecoder]
 * using Android's [android.graphics.BitmapFactory], based on the Skia library. This
 * works well in most circumstances and has reasonable performance, however it has some problems
 * with grayscale, indexed and CMYK images.
 */
public class SkiaImageDecoder @JvmOverloads constructor(
	private val bitmapConfig: Bitmap.Config = Bitmap.Config.RGB_565,
) : ImageDecoder {

	@SuppressLint("DiscouragedApi")
	@Throws(Exception::class)
	override fun decode(context: Context, uri: Uri, sampleSize: Int): Bitmap {
		val options = BitmapFactory.Options()
		options.inPreferredConfig = bitmapConfig
		options.inSampleSize = sampleSize
		return when (uri.scheme) {
			URI_SCHEME_RES -> {
				decodeResource(context, uri, options)
			}

			URI_SCHEME_ZIP -> {
				val file = ZipFile(uri.schemeSpecificPart)
				val entry = file.getEntry(uri.fragment)
				file.use {
					BitmapFactory.decodeStream(it.getInputStream(entry), null, options)
				}
			}

			URI_SCHEME_FILE -> {
				val path = uri.schemeSpecificPart
				if (path.startsWith(URI_PATH_ASSET, ignoreCase = true)) {
					val assetName = path.substring(URI_PATH_ASSET.length)
					context.assets.decodeBitmap(assetName, options)
				} else {
					BitmapFactory.decodeFile(path, options)
				}
			}

			else -> {
				context.contentResolver.decodeBitmap(uri, options)
			}
		} ?: throw RuntimeException(DECODER_NULL_MESSAGE)
	}

	public class Factory @JvmOverloads constructor(
		override val bitmapConfig: Bitmap.Config = Bitmap.Config.RGB_565,
	) : DecoderFactory<SkiaImageDecoder> {

		override fun make(): SkiaImageDecoder {
			return SkiaImageDecoder(bitmapConfig)
		}
	}
}
