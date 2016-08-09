## SocialWrapper
Make the implementation of Facebook SDK become more simple on Android
 - Simplify steps to integrate Facebook SDK to project.
 - Wrap some common features, make the way to login, share content become easy and clear.

```

  public class MainActivity extends SimpleFacebookActivity {
    private Button btn_login_fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_login_fb = (Button) findViewById(R.id.btn_login_fb);
        btn_login_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLogin();
            }
        });
    }
  }
  
```


### Download
Configure your project-level `build.gradle`:

```
buildscript {
  repositories {
    mavenCentral()
        maven {
            url 'https://dl.bintray.com/skywander/maven/'
        }
   }
}
```

Add the SocialWrapper dependencies:
```
compile 'com.worklab.social_gateway:simple-social:0.0.3'
```
