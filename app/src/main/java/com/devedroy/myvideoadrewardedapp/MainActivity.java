package com.devedroy.myvideoadrewardedapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.devedroy.myvideoadrewardedapp.databinding.ActivityMainBinding;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    public static final String REWARD_AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917";

    private RewardedAd mRewardedAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MobileAds.initialize(this, initializationStatus -> {

        });

        loadRewardedAds();

        binding.btnLoadAd.setOnClickListener(view -> {
            showRewardedAd();
        });
    }

    private void showRewardedAd() {
        if (mRewardedAd != null) {
            mRewardedAd.show(MainActivity.this, rewardItem -> {
                int amount = rewardItem.getAmount();
                String type = rewardItem.getType();
                binding.tvInfo.setText("The content is unlocked!");
            });
        } else {
            Toast.makeText(this, "Reward ad is not ready yet!", Toast.LENGTH_LONG).show();
        }
    }

    private void loadRewardedAds() {
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(this, REWARD_AD_UNIT_ID, adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                mRewardedAd = null;
            }

            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                super.onAdLoaded(rewardedAd);
                mRewardedAd = rewardedAd;

                rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdClicked() {
                        super.onAdClicked();
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                    }

                    @Override
                    public void onAdImpression() {
                        super.onAdImpression();
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        super.onAdShowedFullScreenContent();
                    }
                });
            }
        });
    }
}