package com.googlefitdemo.databinding;
import com.googlefitdemo.R;
import com.googlefitdemo.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityMainBindingImpl extends ActivityMainBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.guideline, 4);
        sViewsWithIds.put(R.id.rltRootCalories, 5);
        sViewsWithIds.put(R.id.imgRightTopCalories, 6);
        sViewsWithIds.put(R.id.imgTodayCalories, 7);
        sViewsWithIds.put(R.id.rltCalories, 8);
        sViewsWithIds.put(R.id.imgCalories, 9);
        sViewsWithIds.put(R.id.txtCalories, 10);
        sViewsWithIds.put(R.id.rltRootDistance, 11);
        sViewsWithIds.put(R.id.imgRightTopDistance, 12);
        sViewsWithIds.put(R.id.imgTodayDistance, 13);
        sViewsWithIds.put(R.id.rltDistance, 14);
        sViewsWithIds.put(R.id.imgDistance, 15);
        sViewsWithIds.put(R.id.txtDistance, 16);
        sViewsWithIds.put(R.id.rltRootSteps, 17);
        sViewsWithIds.put(R.id.imgRightTopSteps, 18);
        sViewsWithIds.put(R.id.imgTodaySteps, 19);
        sViewsWithIds.put(R.id.rltSteps, 20);
        sViewsWithIds.put(R.id.imgSteps, 21);
        sViewsWithIds.put(R.id.txtSteps, 22);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityMainBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 23, sIncludes, sViewsWithIds));
    }
    private ActivityMainBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.constraintlayout.widget.Guideline) bindings[4]
            , (android.widget.ImageView) bindings[9]
            , (android.widget.ImageView) bindings[15]
            , (android.widget.ImageView) bindings[6]
            , (android.widget.ImageView) bindings[12]
            , (android.widget.ImageView) bindings[18]
            , (android.widget.ImageView) bindings[21]
            , (android.widget.ImageView) bindings[7]
            , (android.widget.ImageView) bindings[13]
            , (android.widget.ImageView) bindings[19]
            , (android.widget.RelativeLayout) bindings[8]
            , (android.widget.RelativeLayout) bindings[14]
            , (android.widget.RelativeLayout) bindings[5]
            , (android.widget.RelativeLayout) bindings[11]
            , (android.widget.RelativeLayout) bindings[17]
            , (android.widget.RelativeLayout) bindings[20]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[0]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[16]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[22]
            , (android.widget.TextView) bindings[3]
            );
        this.rootLayout.setTag(null);
        this.txtCaloriesValue.setTag(null);
        this.txtDistanceValue.setTag(null);
        this.txtStepsValue.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.fitnessData == variableId) {
            setFitnessData((com.googlefitdemo.FitnessDataResponseModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setFitnessData(@Nullable com.googlefitdemo.FitnessDataResponseModel FitnessData) {
        this.mFitnessData = FitnessData;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.fitnessData);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        com.googlefitdemo.FitnessDataResponseModel fitnessData = mFitnessData;
        java.lang.String javaLangStringFitnessDataSteps = null;
        float fitnessDataSteps = 0f;
        float fitnessDataCalories = 0f;
        java.lang.String javaLangStringFitnessDataCalories = null;
        float fitnessDataDistance = 0f;
        java.lang.String javaLangStringFitnessDataDistance = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (fitnessData != null) {
                    // read fitnessData.steps
                    fitnessDataSteps = fitnessData.steps;
                    // read fitnessData.calories
                    fitnessDataCalories = fitnessData.calories;
                    // read fitnessData.distance
                    fitnessDataDistance = fitnessData.distance;
                }


                // read ("") + (fitnessData.steps)
                javaLangStringFitnessDataSteps = ("") + (fitnessDataSteps);
                // read ("") + (fitnessData.calories)
                javaLangStringFitnessDataCalories = ("") + (fitnessDataCalories);
                // read ("") + (fitnessData.distance)
                javaLangStringFitnessDataDistance = ("") + (fitnessDataDistance);
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtCaloriesValue, javaLangStringFitnessDataCalories);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtDistanceValue, javaLangStringFitnessDataDistance);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtStepsValue, javaLangStringFitnessDataSteps);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): fitnessData
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}