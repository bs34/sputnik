-keepattributes Signature
-keepattributes *Annotation*
-keepattributes EnclosingMethod
-keepattributes InnerClasses
-assumenosideeffects class android.util.Log {
    public static *** w(...);
    public static *** d(...);
    public static *** v(...);
}