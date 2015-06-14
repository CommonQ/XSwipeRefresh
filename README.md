# XSwipeRefresh
================

A SwipeRefreshLayout extension that allows to swipe in both direction


### Demo
[Download apk](/demo.apk)

========

### Usage

If you want an example on how to use it, you can find an example app in this repo.

```
<io.github.commonq.lib.RefreshLayout
    android:id="@+id/swipe_container"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <ListView
        android:id="@+id/list"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#FFFFFF"
        android:dividerHeight="1px" />
</io.github.commonq.lib.RefreshLayout>
```

```
mRefreshLayout.setOnLoadListener(new OnLoadListener() {
            @Override
            public void onLoad() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        values.add("Swipe Up to Load More " + values.size());
                        mArrayAdapter.notifyDataSetChanged();
                        mRefreshLayout.setLoading(false);
                    }
                }, 2000);
            }
        });
```

========

### Customization

```
mRefreshLayout.setDirection(SwipyRefreshLayoutDirection.TOP);
```
OR
```
mRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTTOM);
```
OR
```
mRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);
```
OR
```
mRefreshLayout.setDirection(SwipyRefreshLayoutDirection.NONE);
```



========

### License

```
Copyright (c) 2015 CommonQ
 
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
 
  http://www.apache.org/licenses/LICENSE-2.0
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
```
