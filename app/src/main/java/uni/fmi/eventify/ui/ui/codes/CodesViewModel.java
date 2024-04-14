package uni.fmi.eventify.ui.ui.codes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CodesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CodesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}