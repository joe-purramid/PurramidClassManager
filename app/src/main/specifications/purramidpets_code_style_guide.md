# The Purramid - Code Style Guide

## Naming Conventions
- Naming: PurramidPets can be abbreviated as PurrPets in file titles for efficiency
- Activities: *Activity (e.g., PurrPetsActivity)
- Services: *Service (e.g., PurrPetsService)
- ViewModels: *ViewModel
- Repositories: *Repository
- Use lowercase for packages: com.purramid.purramidpets.*

## Resolution standards
- Pixel density must support 2k and 4k resolutions
- Default resolution is 4k
- 1k is unnecessary
- 8k is unnecessary at this time

## State Management
- Use sealed classes for UI states
- Use data classes for preferences
- Immutable data wherever possible

## Coroutines
- Use viewModelScope in ViewModels
- Use lifecycleScope in Activities
- Structured concurrency for Services

## Resource Organization
- Drawables: ic_* for icons, bg_* for backgrounds
- Strings: Organized by feature in strings.xml

## Button Activation
- Button icons will be 757575 when inactive (default state)
- Button icons will be 2196F3 when active (active state)
- Button background will be E3F2FD when active
- A ripple effect will occur with the onTouch event that activates the button.
- Use isActivated property for boolean on/off states

## Message Boxes
- Messages should appear in snackbars not toasts
- Snackbars should open near the button that triggered the error message
- If action buttons are included in the snackbars
	- Cancel buttons
		- Appear on the left
		- Have a transparent background
		- Have an outline color of 2196F3
	- Accept buttons
		- Appear on the right
		- Have a color 2196F3 for the background fill and the outline