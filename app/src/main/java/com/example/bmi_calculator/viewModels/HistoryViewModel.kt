import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class Measurement(val date: String, val weight: Double, val height: Double, val bmi: String, val unitSystem: String)

class HistoryViewModel : ViewModel() {
    private val PREFS_NAME = "BMI_HISTORY"
    private val _historyList = MutableLiveData<List<Measurement>>()
    val historyList: LiveData<List<Measurement>> get() = _historyList

    init {
        _historyList.value = emptyList()
    }

    fun addMeasurement(measurement: Measurement, context: Context) {
        val currentList = _historyList.value.orEmpty().toMutableList()
        currentList.add(0, measurement)
        if (currentList.size > 10) {
            currentList.removeAt(10)
        }
        _historyList.value = currentList
        saveHistoryToSharedPreferences(context)
    }
    private fun saveHistoryToSharedPreferences(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val history = historyList.value
        val historyJson = Gson().toJson(history)
        prefs.edit().putString("history", historyJson).apply()
    }

    fun loadHistoryFromSharedPreferences(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val historyJson = prefs.getString("history", null)
        if (historyJson != null) {
            val history = Gson().fromJson<List<Measurement>>(historyJson, object : TypeToken<List<Measurement>>() {}.type)
            _historyList.value = history
        }
    }
}
