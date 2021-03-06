package com.synerise.sdk.sample.ui.promotion.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.synerise.sdk.promotions.model.promotion.Promotion;
import com.synerise.sdk.promotions.model.promotion.PromotionDiscountType;
import com.synerise.sdk.promotions.model.promotion.PromotionImage;
import com.synerise.sdk.sample.R;
import com.synerise.sdk.sample.util.DataActionListener;
import com.synerise.sdk.sample.util.ViewUtils;

import java.util.List;

class PromotionsViewHolder extends RecyclerView.ViewHolder {

    private final SimpleDraweeView image;
    private final TextView name;
    private final TextView discount;
    private final TextView expire;
    private final ProgressBar imageProgressBar;

    private final Context context;
    private Promotion promotion;

    PromotionsViewHolder(View itemView, final DataActionListener<Promotion> listener) {
        super(itemView);
        context = itemView.getContext();
        itemView.setOnClickListener(view -> {
            if (promotion != null) listener.onDataAction(promotion);
        });
        image = itemView.findViewById(R.id.promotion_image);
        imageProgressBar = itemView.findViewById(R.id.image_progress_bar);
        name = itemView.findViewById(R.id.promotion_name);
        discount = itemView.findViewById(R.id.promotion_discount);
        expire = itemView.findViewById(R.id.promotion_expire);
    }

    void populateData(@NonNull Promotion promotion) {
        this.promotion = promotion;
        List<PromotionImage> images = promotion.getImages();
        if (images != null) {
            if (!images.isEmpty()) {
                ViewUtils.loadImage(images.get(0).getUrl(), image, imageProgressBar);
            }
        }
        this.name.setText(promotion.getHeadline());
        if (promotion.getExpireAt() != null) {
            this.expire.setText(context.getString(R.string.default_valid_to, DateFormat.format("dd.MM.yyyy", promotion.getExpireAt())));
        }
        if (promotion.getDiscountType() == PromotionDiscountType.AMOUNT) {
            this.discount.setText(context.getString(R.string.default_value_dollar, String.valueOf(promotion.getDiscountValue())));
        } else if (promotion.getDiscountType() == PromotionDiscountType.PERCENT) {
            this.discount.setText(context.getString(R.string.default_value_percent, String.valueOf(promotion.getDiscountValue())));
        } else {
            this.discount.setText(String.valueOf(promotion.getDiscountValue()));
        }
    }
}