package ie.toxodev.retailinmotiontask.supportClasses

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

object OutputManager {
    private var alertDialog: AlertDialog? = null

    fun displayPositiveOnlyAlert(context: Context, title: String, message: String) {
        alertDialog = AlertDialog.Builder(context).apply {
            this.setTitle(title)
            this.setMessage(message)
            setCancelable(false)
                .setPositiveButton("Dismiss", DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                    alertDialog = null
                })
        }.create().also {
            it.show()
        }

    }
}