# Project 2 - *New York Times Article Search*

**New York Times Article Search** is an android app that allows a user to search for articles on web using simple filters. The app utilizes [New York Times Search API](http://developer.nytimes.com/docs/read/article_search_api_v2).

Time spent: **25** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **search for news article** by specifying a query and launching a search. Search displays a grid of image results from the New York Times Search API.
* [x] User can **scroll down to see more articles**. The maximum number of articles is limited by the API search.
* [x] User can tap on any image in results to see the full text of article **full-screen**

The following **optional** features are implemented:

* [ ] Used the **ActionBar SearchView** or custom layout as the query box
* [x] User can **share an article link** to their friends or email it to themselves
* [x] Improved the user interface and experiment with image assets and/or styling and coloring
* [x] User can click on "settings" which allows selection of **advanced search options** to filter results
  * [x] User can configure advanced search filters such as:
    * [x] Begin Date (using a date picker)
    * [x] News desk values (Arts, Fashion & Style, Sports)
    * [x] Sort order (oldest or newest)
  * [x] Subsequent searches have any selected filters applied to the results
  * [ ] Uses a lightweight modal dialog for filters rather than an activity
* [ ] Replaces the default ActionBar with a [Toolbar](http://guides.codepath.com/android/Using-the-App-ToolBar).
* [ ] Apply the popular [Butterknife annotation library](http://guides.codepath.com/android/Reducing-View-Boilerplate-with-Butterknife) to reduce view boilerplate.
* [ ] Replace `GridView` with the [RecyclerView](http://guides.codepath.com/android/Using-the-RecyclerView) and the `StaggeredGridLayoutManager` to improve the grid of image results displayed.
* [ ] Use Parcelable instead of Serializable leveraging the popular [Parceler library](http://guides.codepath.com/android/Using-Parceler).
* [x] Replace Picasso with [Glide](http://inthecheesefactory.com/blog/get-to-know-glide-recommended-by-google/en) for more efficient image rendering.
* [x] Before an article search is triggered by the user, displays the current top stories of the day by default.
* [ ] Hides the `Toolbar` at the top as the user scrolls down through the results using the [CoordinatorLayout and AppBarLayout](http://guides.codepath.com/android/Using-the-App-ToolBar#reacting-to-scroll).
* [ ] Leverage the popular [GSON library](http://guides.codepath.com/android/Using-Android-Async-Http-Client#decoding-with-gson-library) to streamline the parsing of JSON data and avoid manual parsing.

The following **additional** features are implemented:

* [x] List anything else that you can get done to improve the app functionality!
  * Added a placeholder image to display if a given article displayed no images

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://imgur.com/8qOWJco.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.

Emulator API incompatibilities, computer slowness, and random bugs in the emulator-Android Studio connection were the main difficulties I encountered this week. At one point I spent several hours debugging one line of code, and at some random point after lots of stepping through breakpoints, the program just started working (no changes to the code). 

The biggest issue I saw during development, for me and also most of the other students, was the API request limit. When we requested API keys, a lot of students didn't receive one. We mostly used the ones that were listed on the assignment page, which led to buggy loading and exceeding the number of requests. It was hard to test our apps when the articles weren't loading at all.

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library for Android


## License

    Copyright [2016] [copywrite owner]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
