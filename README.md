# Android lists

One data set, shown in four ways. The data is a catalog of movies (title, year, genre, rating, description). Each module opens one screen with a `Spinner` that switches between small demos of the same component.

## Modules

| Module | What it shows |
|---|---|
| `core` | Pure Java. Model, in-memory repository, seeded data generator, grouping by genre, a model of view reuse (`ListSimulator`), `CallCounter`, JUnit tests |
| `scrollview` | `ScrollView` with a hand-built list: `List`, `Card`, `Strip`, `Nested` |
| `listview` | `ListView` and adapters: `Simple`, `Two lines`, `Checked`, `Map`, `Edit`, `Variants`, `Checkbox`, `List in ScrollView` |
| `rv-basic` | `RecyclerView` basics: `Basic`, `Click`, `Recycle log` |
| `rv-layouts` | Layout managers, item types and snapping: `Layouts`, `Sections`, `Carousel` |
| `rv-animations` | Item animations, drag and swipe: `Animations`, `Touch` |
| `rv-mistakes` | Eight common mistakes with a "Fix it" switch |
| `rv-pool` | View lifecycle callbacks, one shared `RecycledViewPool` for several lists, nested lists |
| `app` | An empty module made by the IDE project wizard. The demos do not use it. |

## Find a demo

A `Spinner` item is the class name without `Mode`: `Two lines` is `TwoLinesMode`. Adapters and holders of a demo are in the same package. The package is the module name without the hyphen (`rv-basic` is `org.demo.lists.rvbasic`).

| Module | Spinner item | Entry class | Helpers |
|---|---|---|---|
| `scrollview` | `List` | `ListMode` | `InflateMode` (enum with the three `inflate` calls) |
| `scrollview` | `Card`, `Strip`, `Nested` | `CardMode`, `StripMode`, `NestedMode` | |
| `listview` | `Simple`, `Checked`, `Map`, `Edit` | `SimpleMode`, `CheckedMode`, `MapMode`, `EditMode` | |
| `listview` | `Two lines` | `TwoLinesMode` | `MovieAdapter` |
| `listview` | `Variants` | `VariantsMode` | `VariantAdapter` |
| `listview` | `Checkbox` | `CheckboxMode` | `CheckAdapter` |
| `listview` | `List in ScrollView` | `ScrollNestedMode` | `VariantAdapter` |
| `rv-basic` | `Basic`, `Click`, `Recycle log` | `BasicMode`, `ClickMode`, `RecycleLogMode` | `MovieAdapter`, `ClickAdapter`, `RecycleLogAdapter` |
| `rv-layouts` | `Layouts` | `LayoutsMode` | `CardAdapter`, `SpacingDecoration` |
| `rv-layouts` | `Sections` | `SectionsMode` | `SectionAdapter`, `GenreSpanLookup`, `ListRow` |
| `rv-layouts` | `Carousel` | `CarouselMode` | `CardAdapter` |
| `rv-animations` | `Animations`, `Touch` | `AnimationsMode`, `TouchMode` | `EditableMovieAdapter`, `MovieTouchCallback` |
| `rv-mistakes` | eight mistakes | `NoLayoutManagerMode`, `NoAdapterMode`, `AttachToRootMode`, `LookupInBindMode`, `NotifyAllMode`, `NoNotifyMode`, `StateInViewMode`, `TallItemMode` | `MistakeMode` (shared base class) |
| `rv-pool` | `Lifecycle`, `Two lists`, `Nested` | `LifecycleMode`, `TwoListsMode`, `NestedMode` | `LifecycleAdapter`, `PoolAdapter`, `GenreRowAdapter` |

## What each module demonstrates

- **`scrollview`**: `LayoutInflater.inflate` with `attachToRoot` true, false and `root = null`; `scrollTo` and `scrollBy`; `HorizontalScrollView`; `NestedScrollView`; 10, 200 and 1000 rows.
- **`listview`**: `ArrayAdapter`, `SimpleAdapter`, choice mode, a custom `getView` in three variants (no `convertView`, with `convertView`, with a `ViewHolder`), add and remove with `notifyDataSetChanged`.
- **`rv-basic`**: `Adapter`, `ViewHolder`, `LinearLayoutManager`, two click styles, a log of `onViewRecycled`.
- **`rv-layouts`**: `GridLayoutManager`, `StaggeredGridLayoutManager`, `ItemDecoration`, several view types with `SpanSizeLookup`, `SnapHelper`.
- **`rv-animations`**: `ItemAnimator`, point `notify` calls, `ItemTouchHelper`.
- **`rv-mistakes`**: eight common mistakes, each with a broken and a fixed version.
- **`rv-pool`**: `onViewAttachedToWindow`, `onViewDetachedFromWindow`, `onViewRecycled`, `RecycledViewPool`.

## Run

Open the project in Android Studio and run the module you need. Or from the command line:

```
./gradlew :listview:installDebug
```

Replace `listview` with `scrollview`, `rv-basic`, `rv-layouts`, `rv-animations`, `rv-mistakes` or `rv-pool`.

## Test

```
./gradlew :core:test
```

The screens have no UI tests.

## Requirements

- JDK 17
- Android SDK 34
- The Gradle wrapper in this repository (Gradle 8.0). Android Gradle plugin 8.1.1. Plugin and library versions are in `gradle/libs.versions.toml`.

## Notes

- Java 8 source level, `minSdk` 26.
- The lists have 200 movies. The data generator returns the same movies for the same seed.
- Screens show call counters (`inflate`, `findViewById`, bind). They count calls, not time. Numbers depend on the device and on how you scroll.
- Not covered: `DiffUtil`, `Paging`, Jetpack Compose.
