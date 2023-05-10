package com.example.memorymatch;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.memorymatch.databinding.ActivityGameBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GameActivity extends AppCompatActivity {

    ActivityGameBinding binding;

    private final int[] imageResource = new int[]{R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4,
            R.drawable.image5, R.drawable.image6, R.drawable.image7, R.drawable.image8};
    private final int[] imageIndex = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 0, 1, 2, 3, 4, 5, 6, 7};
    private final List<Integer> solvedImageIndex = new ArrayList<>();
    private final Handler handler = new Handler();

    private int selectedImageIndex = -1;
    private int selectedPosition = -1;
    private int score = 0;

    private View previousItemView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        shuffleArray(imageIndex);

        ImageAdapter imageAdapter = new ImageAdapter(this);
        binding.gvGambar.setAdapter(imageAdapter);
        binding.gvGambar.setOnItemClickListener((parent, view, position, id) -> {
            if (solvedImageIndex.contains(imageIndex[position]) || position == selectedPosition)
                return;

            selectedPosition = position;
            ((ImageView) view).setImageResource(imageResource[imageIndex[position]]);

            if (selectedImageIndex == -1) {
                selectedImageIndex = imageIndex[position];
                previousItemView = view;
                return;
            }

            if (selectedImageIndex == imageIndex[position]) {
                score += 10;
                solvedImageIndex.add(imageIndex[position]);

                if (solvedImageIndex.size() == 8){
                    Toast.makeText(GameActivity.this, "Menang yeee", Toast.LENGTH_SHORT).show();
                    binding.gvGambar.setAdapter(imageAdapter); // reset gambar
                    shuffleArray(imageIndex); // acak ulang
                    score = 0;
                }
            } else {
                handler.postDelayed(() -> {
                    ((ImageView) previousItemView).setImageResource(R.drawable.baseline_question_mark_24);
                    ((ImageView) view).setImageResource(R.drawable.baseline_question_mark_24);
                }, 500);

                score -= 2;
                Toast.makeText(GameActivity.this, "Nope", Toast.LENGTH_SHORT).show();
            }

            binding.tvScore.setText("Score: " + score);
            selectedImageIndex = -1;
            selectedPosition = -1;
        });
    }

    private void shuffleArray(int[] array) {
        Random random = ThreadLocalRandom.current();

        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
}