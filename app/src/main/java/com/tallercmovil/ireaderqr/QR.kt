package com.tallercmovil.ireaderqr

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import java.net.MalformedURLException
import java.net.URL


class QR : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private val PERMISO_CAMARA = 1
    private var scannerView: ZXingScannerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        scannerView = ZXingScannerView(this)
        setContentView(scannerView)

        scannerView?.setResultHandler(this)

        scannerView?.startCamera()
    }

    override fun handleResult(p0: Result?) {
        //Aquí llega lo que se lee del código QR

        val scanResult = p0?.text

        Log.d("QR_LEIDO", scanResult!!)

        try{
            val url = URL(scanResult)
            val i = Intent(Intent.ACTION_VIEW)
            i.setData(Uri.parse(scanResult))
            startActivity(i)
            finish()
        }catch(e: MalformedURLException){
            AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage("El código QR no es válido para la app")
                .setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                    finish()
                })
                .create()
                .show()
        }
    }
}