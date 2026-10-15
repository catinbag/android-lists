# Android lists

One data set, shown in four ways. The data is a catalog of movies (title, year, genre, rating, description). Each module opens one screen with a `Spinner` that switches between small demos of the same component.

## Modules

| Module | What it shows |
|---|---|
| `core` | Pure Java. Model, in-memory repository, seeded data generator, grouping by genre, a model of view reuse (`ListSimulator`), `CallCounter`, JUnit tests |
| `scrollview` | `ScrollView` with a hand-built list: `List`, `Card`, `Strip`, `Nested` |
| `listview` | `ListView` and adapters: `Simple`, `Two lines`, `Checked`, `Map`, `Edit`, `Variants`, `Checkbox`, `List in ScrollView` |
| `rv-basic` | `RecyclerView` basics: `Basic`, `Click`, `Recycle log`, `Layouts`, `Sections`, `Carousel` |
| `rv-interaction` | Item animations, drag and swipe, and eight common mistakes with a "Fix it" switch |
| `rv-pool` | View lifecycle callbacks, one shared `RecycledViewPool` for several lists, nested lists |
| `app` | An empty module made by the IDE project wizard. The demos do not use it. |

## What each module demonstrates

- **`scrollview`**: `LayoutInflater.inflate` with `attachToRoot` true, false and `root = null`; `scrollTo` and `scrollBy`; `HorizontalScrollView`; `NestedScrollView`; 10, 200 and 1000 rows.
- **`listview`**: `ArrayAdapter`, `SimpleAdapter`, choice mode, a custom `getView` in three variants (no `convertView`, with `convertView`, with a `ViewHolder`), add and remove with `notifyDataSetChanged`.
- **`rv-basic`**: `Adapter`, `ViewHolder`, `LinearLayoutManager`, `GridLayoutManager`, `StaggeredGridLayoutManager`, `ItemDecoration`, several view types with `SpanSizeLookup`, `SnapHelper`, two click styles.
- **`rv-interaction`**: `ItemAnimator`, point `notify` calls compared with `notifyDataSetChanged`, `ItemTouchHelper`.
- **`rv-pool`**: `onViewAttachedToWindow`, `onViewDetachedFromWindow`, `onViewRecycled`, `RecycledViewPool`.

## Run

Open the project in Android Studio and run the module you need. Or from the command line:

```
./gradlew :listview:installDebug
```

Replace `listview` with `scrollview`, `rv-basic`, `rv-interaction` or `rv-pool`.

## Test

```
./gradlew :core:test
```

The screens have no UI tests.

## Requirements

- JDK 17
- Android SDK 34
- The Gradle wrapper in this repository. Plugin and library versions are in `gradle/libs.versions.toml`.

## Notes

- Java 8 source level, `minSdk` 26.
- The lists have 200 movies. The data generator returns the same movies for the same seed.
- Screens show call counters (`inflate`, `findViewById`, bind). They count calls, not time. Numbers depend on the device and on how you scroll.
- Not covered: `DiffUtil`, `Paging`, Jetpack Compose.
