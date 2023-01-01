package com.ruchanokal.mytmdbmovieapp.fragments

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.core.view.drawToBitmap
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.ruchanokal.mytmdbmovieapp.R
import com.ruchanokal.mytmdbmovieapp.databinding.FragmentMovieDetailsBinding
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*


class MovieDetailsFragment : Fragment() {

    private val TAG = "MovieDetailsFragment"
    private var binding: FragmentMovieDetailsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_movie_details,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            Log.e(TAG,"arguments")

            val movie = MovieDetailsFragmentArgs.fromBundle(it).movie
            binding!!.moviedetails = movie

            binding!!.shareYourMovie.setOnClickListener {

                //filmin ekran görüntüsü alıp istediğiniz kişiyle paylaşmanızı sağlar
                share()

            }



        }

    }
    @Suppress("DEPRECATION")
    private fun saveImageToStorage(
        bitmap: Bitmap,
        filename: String,
        mimeType: String = "image/jpeg",
        directory: String = Environment.DIRECTORY_PICTURES,
        mediaContentUri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    ): Uri? {
        val imageOutStream: OutputStream?
        val imageUri: Uri?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val values = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, filename)
                put(MediaStore.Images.Media.MIME_TYPE, mimeType)
                put(MediaStore.Images.Media.RELATIVE_PATH, directory)
            }

            requireActivity().contentResolver.run {
                imageUri = requireActivity().contentResolver.insert(mediaContentUri, values)
                imageOutStream = imageUri?.let { openOutputStream(it) }
            }
        } else {
            val imagePath = Environment.getExternalStoragePublicDirectory(directory).absolutePath
            val image = File(imagePath, filename)
            imageOutStream = FileOutputStream(image)

            val authority = requireActivity().packageName + ".provider"

            imageUri = FileProvider.getUriForFile(
                requireActivity(),
                authority,
                image
            )
        }

        imageOutStream?.use { bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it) }

        return imageUri
    }

    private fun getViewBitmap(): Bitmap = binding!!.allView.drawToBitmap(Bitmap.Config.ARGB_8888)

    fun share(){

        val now = Date()
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now)

        val viewBitmap = getViewBitmap()
        val mimeType = "image/jpeg"
        val imageUri = saveImageToStorage(
            viewBitmap,
            filename = "${now}.jpg",
            mimeType = mimeType
        )
        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, imageUri)
            type = mimeType
        }
        startActivity(Intent.createChooser(shareIntent, "Share with:"))
    }


}