# ScrollingViewPagerIndicator
[![Release](https://jitpack.io/v/Seik/ScrollingViewPagerIndicator.svg)](https://jitpack.io/#Seik/ScrollingViewPagerIndicator)

Custom view for indicate position.

## How it looks like

<img src="https://i.imgur.com/AqiFdew.gif">

## Installation
In your project level build.gradle :

```java
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

In your app level build.gradle :

```java
dependencies {
    implementation 'com.github.Seik:ScrollingViewPagerIndicator:{latest_version}'
}
```

## Usage

In your view:

```xml
<android.support.v4.view.ViewPager
  android:id="@+id/viewPager"
  android:layout_width="match_parent"
  android:layout_height="match_parent" />

<me.ivmg.scrollingviewpagerindicator.ScrollViewPagerIndicator
  android:id="@+id/scrollView"
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  app:dotSize="16dp"
  app:gapSize="3dp"
  app:visibleItems="5" />
```

In your code:

```java
final ViewPager viewPager = findViewById(R.id.viewPager);
final ScrollViewPagerIndicator scrollViewPagerIndicator = findViewById(R.id.scrollView);

viewPager.setAdapter(new CustomPagerAdapter(this));

scrollViewPagerIndicator.attachViewPager(viewPager);
```
