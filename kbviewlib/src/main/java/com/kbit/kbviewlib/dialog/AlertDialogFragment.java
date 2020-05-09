package dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

public class AlertDialogFragment  extends DialogFragment {

    private static final String KEY_TITLE = "title";

    private static final String KEY_MESSAGE = "message";

    private static final String KEY_POSITIVE_BUTTON_TEXT = "positiveButtonText";

    private static final String KEY_NEGATIVE_BUTTON_TEXT = "negativeButtonText";

    private static final String KEY_CANCELABLE = "cancelable";

    private DialogInterface.OnClickListener mPositiveButtonListener;
    private DialogInterface.OnClickListener mNegativeButtonListener;

    public void setPositiveButtonListener(DialogInterface.OnClickListener listener) {
        mPositiveButtonListener = listener;
    }

    public void setNegativeButtonListener(DialogInterface.OnClickListener listener) {
        mNegativeButtonListener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        String positiveButtonText = args.getString(KEY_POSITIVE_BUTTON_TEXT);
        String negativeButtonText = args.getString(KEY_NEGATIVE_BUTTON_TEXT);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(args.getString(KEY_TITLE))
                .setMessage(args.getString(KEY_MESSAGE))
                .setCancelable(args.getBoolean(KEY_CANCELABLE));
        if (positiveButtonText != null) {
            builder.setPositiveButton(positiveButtonText, mPositiveButtonListener);
        }
        if (negativeButtonText != null) {
            builder.setNegativeButton(negativeButtonText, mNegativeButtonListener);
        }
        return builder.create();
    }

    public static class Builder {

        private AlertDialogFragment mDialog;
        private String mTitle;
        private String message;
        private String mTag;
        private String mPositiveButtonText;
        private String mNegativeButtonText;
        private boolean mCancelable;
        private Context mContext;

        public Builder(Context context) {
            mContext = context;
            mDialog = new AlertDialogFragment();
        }

        public Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            mCancelable = cancelable;
            return this;
        }

        public Builder setTag(String tag) {
            mTag = tag;
            return this;
        }

        public Builder setPositiveButtonListener(@StringRes int textId, DialogInterface.OnClickListener listener) {
            mPositiveButtonText = mContext.getString(textId);
            mDialog.setPositiveButtonListener(listener);
            return this;
        }

        public Builder setPositiveButtonListener(String text, DialogInterface.OnClickListener listener) {
            mPositiveButtonText = text;
            mDialog.setPositiveButtonListener(listener);
            return this;
        }

        public Builder setNegativeButtonListener(@StringRes int textId, DialogInterface.OnClickListener listener) {
            mNegativeButtonText = mContext.getString(textId);
            mDialog.setNegativeButtonListener(listener);
            return this;
        }

        public Builder setNegativeButtonListener(String text, DialogInterface.OnClickListener listener) {
            mNegativeButtonText = text;
            mDialog.setNegativeButtonListener(listener);
            return this;
        }

        public AlertDialogFragment show(FragmentManager fm) {
            Bundle args = new Bundle();
            args.putString(KEY_TITLE, mTitle);
            args.putString(KEY_MESSAGE, message);
            args.putBoolean(KEY_CANCELABLE, mCancelable);
            args.putString(KEY_POSITIVE_BUTTON_TEXT, mPositiveButtonText);
            args.putString(KEY_NEGATIVE_BUTTON_TEXT, mNegativeButtonText);
            mDialog.setArguments(args);
            mDialog.show(fm, mTag);
            return mDialog;
        }

    }
}
