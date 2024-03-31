package uni.fmi.eventify.ui.ui.home;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import uni.fmi.eventify.databinding.FragmentHomeBinding;
import uni.fmi.eventify.game.GameView;

public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Display display = getActivity().getWindowManager().getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        GameView view = new GameView(getActivity(),size.x , size.y);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}