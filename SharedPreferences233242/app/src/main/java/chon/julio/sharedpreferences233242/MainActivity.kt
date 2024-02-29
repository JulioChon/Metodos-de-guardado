package chon.julio.sharedpreferences233242

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import  chon.julio.sharedpreferences233242.PreferenceHelper.defaultPreference
import  chon.julio.sharedpreferences233242.PreferenceHelper.password
import  chon.julio.sharedpreferences233242.PreferenceHelper.userId
import  chon.julio.sharedpreferences233242.PreferenceHelper.clearValues
import  chon.julio.sharedpreferences233242.PreferenceHelper.customPreference

import chon.julio.sharedpreferences233242.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    val CUSTOM_PREF_NAME = "User_data"
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.btnSave.setOnClickListener(this)
        binding.btnClear.setOnClickListener(this)
        binding.btnShow.setOnClickListener(this)
        binding.btnShowDefault.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val prefs = customPreference(this, CUSTOM_PREF_NAME)
        when (v?.id) {
            R.id.btnSave -> {
                prefs.password = binding.inPassword.text.toString()
                prefs.userId = binding.inUserId.text.toString().toInt()
            }
            R.id.btnClear -> {
                prefs.clearValues()
            }
            R.id.btnShow -> {
                binding.inUserId.setText(prefs.userId.toString())
                binding.inPassword.setText(prefs.password)
            }
            R.id.btnShowDefault -> {
                val defaultPrefs = defaultPreference(this)
                binding.inUserId.setText(defaultPrefs.userId.toString())
                binding.inPassword.setText(defaultPrefs.password)
            }
        }
    }
}
object PreferenceHelper {

    val USER_ID = "USER_ID"
    val USER_PASSWORD = "PASSWORD"

    fun defaultPreference(context: Context): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun customPreference(context: Context, name: String): SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }

    var SharedPreferences.userId
        get() = getInt(USER_ID, 0)
        set(value) {
            editMe {
                it.putInt(USER_ID, value)
            }
        }

    var SharedPreferences.password
        get() = getString(USER_PASSWORD, "")
        set(value) {
            editMe {
                it.putString(USER_PASSWORD, value)
            }
        }

    fun SharedPreferences.clearValues() {
        editMe {
            it.clear()
        }
    }
}