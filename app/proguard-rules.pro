-ignorewarnings
#-flattenpackagehierarchy
#-dontwarn okio.**
#-dontwarn retrofit2.**


-keep public class com.google.android.gms.ads.**{
   public *;
}


-dontwarn com.google.ads.**

-keep public class com.google.ads.* {*;}


-keep public class com.google.gson.** {
    public protected *;
}

-keep public class com.google.ads.internal.* {*;}
-keep public class com.google.ads.internal.AdWebView.* {*;}
-keep public class com.google.ads.internal.state.AdState {*;}
-keep public class com.google.ads.searchads.* {*;}
-keep public class com.google.ads.util.* {*;}

#-dontshrink
#-dontoptimize
#-keepattributes Annotation
#-optimizationpasses 5
-keep public class com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.GetSet.** {*;}
-keep public class com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.export.** {*;}
-keep public class com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Api.** {*;}
#-optimizations !method/removal/parameter
#-keep public class com.google.android.gms.ads.** {
#    public *;
#}
#
#-keep public class com.google.ads.** {
#    public *;
#}
#-repackageclasses 'o'



