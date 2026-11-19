# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [0.2.0] - 2026-11-19

This prototype is not a finished game. It demonstrates a playable game flow with a menu and game screen, keyboard-controlled movement, spritesheet animation, a Tiled parking-lot environment, camera and viewport handling, and interaction with cars that changes the game state. Visual and audio feedback include plate messages, HUD objective and interaction labels, music, and sound effects, with a mute control. The prototype focuses on the central mechanic and project structure; it does not include every level or final production assets.


### Added

- Added this comprehensive changelog with categorized, dated commit history
- `8460193` Added a YouTube gameplay video to the README files using clickable thumbnail previews.

### Improved

- `4dc8998` Improved not using scaling with decimals, it looks bad.

### Changed

- `ec10b11` Updated the README files to describe the current playable prototype.

## [0.1.24] - 2026-11-16

### Added

- `b2a55b6` Added HUD labels to Level 1: a randomly selected car's plate is shown as the objective, and approaching a car displays an interaction prompt.
- `27a754a` Added a HUD that dynamically displays text labels created by Level children. It supports objective and interaction labels, and the Level updates the HUD when children add or remove labels.

## [0.1.23] - 2026-11-15

### Changed

- `bfddbd9` Refactored code to use shorter lines.

## [0.1.22] - 2026-11-14

### Removed

- `264fcf5` Removed unused parentheses.

### Fixed

- `b0c296d` Fixed car placement to start where its parking-slot rectangle starts after the rectangle's size and position were changed in Tiled. Improved interaction detection: the player now checks proximity to the car's center on the X axis instead of checking parking-slot rectangle collisions.

### Changed

- `7c1eace` Renamed `is` to `has` for clearer naming and added a method to check whether an object is close to the player on only one axis.

### Added

- `db852a9` Added a method to check whether an object is within a specified radius of the player.

## [0.1.21] - 2026-11-12

### Changed

- `7f406b8` Merged the remote-tracking `origin/main` branch.
- `d06ec6c` Renamed the Tiled `cars` object layer to `parkingSlots`, assigned more reasonable object and layer IDs in `level-one.tmx`, and resized/repositioned parking slots to match the real slots. Cars no longer need drawing offsets, and interaction no longer depends on parking-slot overlap.
- `dd42bf8` Renamed the `cars` map object layer to `parkingSlots` and manually adjusted object and layer IDs in `level-one.tmx`.
- `fe1b904` Allowed each level to have its own music.
- `69065ad` Automatically paused music when the game window is hidden.

### Added

- `11f29e7` Added background music and an M-key mute toggle.

## [0.1.20] - 2026-11-11

### Added

- `b3ced9a` Added a pop sound when a plate is seen.
- `ba68714` Added an eye-symbol trail each time a plate is seen, indicating which plate was just viewed.
- `91bbbbc` Added a `Symbol` object for drawing UI symbols.
- `f3b9edd` Added enums for convenient access to UI textures.
- `d8e67b7` Added a `MenuScreen` with options to start a level by number, continue, and return to the menu with Escape. Saving progress is not implemented yet, and some buttons remain placeholders.
- `19a0991` Added a method to test whether a number is within an inclusive `[min, max]` range.

### Changed

- `b6adcc1` Required all drawables to have a position and provide it as a copy (`vector.cpy()`).
- `fcc77b0` Replaced the `Timerizable` interface with an abstract parent class, since its child classes are timerizable themselves.
- `0cb9c82` Renamed `real` to `box` to better describe its use in setting bounds; introduced more descriptive names such as `TILE_SIZE_IN_PIXELS` and `horizontalSpritesheetSpacing`; clarified `Level1.initializeLevel()` and plate generation.

## [0.1.19] - 2026-10-27

### Changed

- `2584587` Removed incorrect use of viewport screen size and added `StaticCar.makePlateMessage()` to keep plate-message creation within the car's responsibility. Updated `Text`: `textScale` is required at construction; background properties are configured when building the message and drawn when the message is drawn; font and background use the original virtual-size projection for consistent scaling; and text centering is clearer and more reliable.
- `4d8d209` Updated `CustomScaling` to avoid conflicts at different resolutions.
- `cf7480a` Renamed virtual-width values to distinguish the width after scaling.

### Improved

- `8388fa2` Improved naming clarity.

## [0.1.18] - 2026-10-24

### Added

- `852ef95` Added TODOs for future improvements.
- `40d5c79` Allowed non-collidable drawables, such as text, to be added.
- `00bb83e` Made virtual and screen dimensions accessible for positioning objects.

### Fixed

- `d7bb526` Fixed text position and size so they remain consistent by setting the screen projection matrix to the scaled virtual size; improved positioning to use the screen viewport size instead of calculated `Gdx.graphics` dimensions.
- `bbcfe35` Fixed `Utilities.genRandomDigits()` so its output length follows `numberOfDigits` instead of always using a fixed `%03d` format.

### Improved

- `487e3ef` Improved accessibility of `static final` values.

### Changed
- `85c46b8` Refactored `Text.draw()`: removed its temporary camera for background projection and correctly ended the sprite batch while `ShapeRenderer` draws the background before restarting the batch for the font.
- `67a15a7` Changed `Collidable.getPosition()` to return a copy rather than the mutable internal position, preventing callers from moving an object without updating its collision bounds.
- `6902e5b` Changed temporaries to use the supplied delta time instead of `Gdx.graphics.getDeltaTime()`, so timers follow the update input and are easier to test.
- `279880e` Renamed identifiers to avoid redundancy.
- `2b0ed42` Corrected the spelling of `collisionable` to `collidable`.
- `0a8b065` Decoupled text from temporization so text can be temporary or permanent; added exception handling for invalid temporization implementations and made temporary updates more efficient and readable.
- `ad5461a` Improved encapsulation and single-responsibility separation by moving level-specific objects into the level package and reusable objects into the general objects package.
- `efb2e2f` Improved encapsulation by moving player creation, input handling, and drawing into `Level`, and handling camera positioning there.

### Removed

- `a580553` Freed the font from memory.

## [0.1.17] - 2026-10-23

### Removed

- `397aff7` Removed an unused method.
- `0601272` Removed unused properties and a method.

### Changed

- `7d552e1` Doubled the displayed game size and smoothed player movement while avoiding black bars by using `ScalingViewport` instead of `FitViewport`.
- `8f44923` Set the game size to 960x960, twice the map size.
- `eed5d95` Tried a visual-artifact fix that worked in windowed mode but made fullscreen worse.

### Fixed

- `450ba37` Fixed the remaining visual artifacts.
- `3e52ac6` Fixed player flicker while temporaries were being deleted by moving update logic out of `Level.render()` and adding an overridable `updateLevel()` phase for level-specific updates.
- `51b3531` Fixed vertical and horizontal black-line artifacts while moving by using integer world coordinates for the camera.

### Removed

- `49327c4` Removed obsolete manual viewport handling from `Level.resize()`.

## [0.1.16] - 2026-10-22

### Added

- `be38f7b` Added getters to support manual positioning.
- `8ddfe8b` Implemented plate messages and used `SimpleDrawable` to handle drawable types. Added `Temporary` handling so timed drawables are disposed and removed from the drawable array when they expire, and added a method for disposing arrays of drawables.
- `f62bcc6` Added a viewport to display the screen appropriately.
- `43d4281` Declared `Message` and `Temporary` classes. Messages accept text, display duration, and a `BitmapFont`; can be positioned directly or centered; and can optionally draw a configurable background and text color. Temporaries display drawables for a specified duration, then dispose and remove them from their array.
- `53708f5` Implemented `Disposable` and declared and implemented `SimpleDrawable` for future use.

### Fixed

- `3e6ce63` Fixed plate formatting so generated digits are zero-padded when below 100 and returned as a string.

### Improved

- `4d9c31b` Improved plate-text size and positioning.
- `4833574` Improved plate validation.

### Changed

- `4e221c1` Made use of `this` consistent.
- `7c7ce1f` Updated Gradle wrapper properties to use a newer version.
- `935e976` Changed a search to find any `BasicDrawable`, rather than only a child of `BasicDrawable`.
- `d4d0407` Made use of `this` consistent.

### Removed

- `e31f4f0` Removed an unnecessary import.
- `ddde45f` Removed a debug `println`.

## [0.1.15] - 2026-09-28

### Added

- `b4762c5` Added basic plate viewing with E: correlate a `StaticCar`'s `parkingSlotId` with the Tiled rectangle object's ID and print the plate to the console.
- `f1d9b1d` Added a `parkingSlotId` parameter to `StaticCar`.
- `b773155` Added a `parkingSlotId` property and getter to correlate a `StaticCar` with its rectangle map object.
- `2adab65` Implemented plate retrieval and used `Set`/`HashSet` to avoid duplicate plates; added the plate parameter to `StaticCar` and completed plate creation.
- `f46d5bd` Added a plate property and `getPlate()` method to `StaticCar`.
- `11a8836` Implemented `Utilities.genPlate()` to generate a plate with three letters and three digits.
- `ead54d6` Added utilities for generating a random letter and a requested number of digits.

## [0.1.14] - 2026-09-27

### Added

- `f9793d1` Added a double-commented debugging note to keep the debug statement disabled.
- `48a2a89` Declared and implemented `DrawableMap`.
- `252a6a3` Added debug drawing for collision bounds.
- `0882056` Added visible-size bounds and collision handling for movable objects. Objects block on overlap, while the map blocks when the player is no longer inside it.
- `b106a20` Added real-size bounds for objects, excluding invisible parts of their sprites.
- `f9e5630` Added a bounds offset to `BasicDrawable` to match visible object size rather than tile size, and added debug drawing for bounds.

### Improved

- `636a4b3` Limited map bounds to the parking area so the player cannot enter the park.
- `c596f11` Improved playability so the player can walk between vehicles.
- `20b5f6d` Improved readability.

## [0.1.13] - 2026-09-26

### Added

- `df56ff1` Added support for updating all movable entities rather than only the player.

### Changed

- `72d6cc3` Improved encapsulation by making bounds private and making bounds and position final where a single instance is retained.

### Removed

- `93ecee0` Removed the old `StaticCar` bounds implementation after moving bounds into `BasicDrawable`.

## [0.1.12] - 2026-09-22

### Changed

- `b78667a` Merged the remote-tracking `origin/main` branch.
- `ab90346` Moved bounds responsibility to a parent class because multiple objects need bounds.

## [0.1.11] - 2026-09-11

### Fixed

- `0d4d99d` Fixed player rendering by passing integer rather than floating-point dimensions to the texture region.
- `ffc8f0e` Fixed player movement coordinates: the new position is now calculated as current position plus traveled distance.

### Improved

- `a91e68c` Improved separation of responsibilities.

### Changed

- `a58fc7e` Recorded a work-in-progress commit.
- `ac81024` Made qualification of methods and attributes with `this` consistent.

## [0.1.10] - 2026-09-04

### Fixed

- `98300ca` Fixed use of position so the proper method is used instead of accessing the field directly.
- `770c398` Fixed incorrect position access and `getPosition()` in `MovableEntity`.
- `c3e3dc1` Fixed incorrect use of `final`.

### Added

- `cc2ef2d` Updated bounds automatically when position is set.
- `e81fe1d` Initialized bounds and replaced sprite width and height with appropriately named constants.

### Changed

- `753a08a` Renamed non-constant SCREAMING_SNAKE_CASE identifiers to lowerCamelCase in drawable and movable-entity classes to follow Java naming conventions.
- `d9b486c` Added use of `StaticEntity` and improved consistency.
- `d009b2a` Made width and height final.
- `510eed1` Kept sprite dimensions, spacing, frames per move, and frame duration with the player where needed; made `this` usage consistent and distinguished stride from spacing direction.
- `ead8d0c` Changed the class to extend the new `BasicDrawable` instead of implementing `Disposable`, adapting it to the parent class and consistently qualifying members with `this`.
- `8432785` Changed `BasicDrawable` from an interface to an abstract parent class so basic drawables share position and bounds; removed its `dispose` implementation to avoid unnecessary coupling.

### Improved

- `f887898` Improved `setPosition()` usage and isolated its behavior.
- `735996c` Improved readability and consistent line breaks.

### Removed

- `ab59e87` Removed the `BasicDrawable` implementation.

## [0.1.9] - 2026-09-02

### Improved

- `fe48b22` Improved readability of a `super` call.

## [0.1.8] - 2026-08-31

### Added

- `ccfac73` Added WASD player movement at a set speed using `MovableEntity`; integrated the player into Level 1.
- `12c8b71` Added `MovableEntity` for four-direction spritesheet movement and idle animation, with configurable tile size, spacing, frames per move, and frame duration; added `MovableState` and `MovableDirection`.
- `b6ffb72` Kept the player fixed in the center of the virtual viewport.
- `dc4cf3a` Added a utility class for storing constants.

### Fixed

- `a671cf1` Fixed warnings and unused imports, made cars final where possible, and replaced explicit generic types with the diamond operator.
- `f00a1b2` Fixed warnings and made drawable references final where possible; replaced explicit generic types with the diamond operator.
- `36801fd` Fixed virtual dimensions and used the viewport to fill unused space with black.
- `a3c8bb5` Fixed car drawing positions to account for the car map object's rectangle margin.

### Changed

- `45d497d` Made virtual width and height protected so `Level` subclasses can access them.
- `7cbeec3` Renamed `Drawable` to `BasicDrawable` to avoid conflict with libGDX's `Drawable`.
- `0aac64f` Moved resize handling into `Level`; each level now chooses its camera position without setting up resizing itself.
- `b34e597` Painted cars using a copy of the color.
- `d82cc27` Allowed car body color to vary while keeping details unchanged.

### Improved

- `8631f2f` Improved car body and detail sprites.
- `29d7451` Added dark-gray and light-gray car colors and removed white, which did not look right.
- `408d6c1` Improved readability.

## [0.1.7] - 2026-08-30

### Added

- `b268760` Implemented `GameScreen` to work with any level without handling game logic itself; configured `NoClickGame` to run the only available level.
- `a2ac033` Added the abstract `Level` class for map, drawable, rendering, and disposal handling; added Level 1 with randomly colored cars and texture initialization, plus a level factory.
- `af1564c` Added a `Drawable` interface for drawables that can be drawn and disposed by the abstract level.
- `d8cbfa9` Added the `Drawable` interface.
- `662a10c` Added a car-drawing class with fixed position, facing, and color, bounds access, drawing, and disposal.
- `17356ac` Added the Tiled `Facing` attribute to the Java project.

### Changed

- `80c6229` Added conversion from strings to enum values for Tiled object properties.
- `3841770` Updated Level 1 pattern-set paths to their actual locations.

### Removed

- `96c1a57` Removed the redundant `car_left.png`.

## [0.1.6] - 2026-08-28

### Added

- `2d49843` Merged the new assets.
- `a5b42b1` Added map and car-layer initialization with a facing attribute.

### Removed

- `f9139e7` Removed an approach that was not suitable.

## [0.1.5] - 2026-08-27

### Changed

- `060a4ee` Renamed `level-one` to `old` to avoid a conflict with another version.

### Added

- `ef4fdf7` Added new assets.
- `9b69ee0` Added new assets.

## [0.1.4] - 2026-08-24

### Added

- `856564f` Added sprites.

### Removed

- `ff90b09` Removed the old car layer.

## [0.1.3] - 2026-08-23

### Added

- `62a3d7c` Added the Level 1 map with a car object layer and a `facing` enum attribute.
- `3fb7ecf` Initialized the `NoClickGame` and `GameScreen` classes.

### Changed

- `d3e413e` Removed `Main.java`, renamed the main class to `NoClickGame`, and updated references.
- `1365776` Changed the background color, window title, text, and button text and behavior for learning purposes.

## [0.1.2] - 2026-07-16

### Changed

- `d21bb0b` Updated license information in `README_en.md`.
- `3040e5d` Changed the license to PolyForm Noncommercial License 1.0.0, updated license information, and removed Creative Commons icons.
- `bbbaf40` Updated the changelog.
- `db15b35` Replaced the Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License with PolyForm Noncommercial License 1.0.0.

## [0.1.1] - 2026-07-14

### Changed

- `7ac331c` Released version 0.1.0 and updated the changelog.
- `2bd7812` Revised the README for clarity and consistency, including updates to the game description and instructions.
- `8b55ff7` Revised the README project details, including the project name and installation instructions.

### Added

- `1e7ef3d` Added project images.

### Removed

- `ae0ca5b` Removed the old README.
- `b081812` Removed the old README.

## [0.1.0] - 2026-07-11

### Fixed

- `8220f81` Fixed a note.
- `fbe461b` Fixed a note.

### Added

- `cdf1fdf` Initialized the project with the `gdx-liftoff` utility.
- `852eef0` Added the Attribution-NonCommercial-ShareAlike 4.0 International license text.
- `dc17071` Added initial repository configuration: project name, developer, description, technologies, wiki URL, and basic compilation and execution instructions.
- `1425d2b` Added the changelog for recording future additions.

### Changed

- `90a8bef` Corrected the URL to the project proposal wiki.
