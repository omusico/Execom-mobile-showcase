Toolbox #3: Material design - Action bar
========================================

Material components and behaviour.
----------------------------------

Google’s material design brought many new features and design guidelines to the Android platform.
A large part of the change is centered around both action bar and status bar especially in the
connection with the Recycler view.

The purpose of this toolbox is to create an application that would mimic the default behaviour of
the Google’s Play Store application status bar, action bar, and screen transitions. This toolbox
would also have to implement one Recycler view along with the needed adapters.

To achieve the effect of scaling image and title text, multiple view layers are used.

# Image view that scales depending on the list position.
# Simple overlay view that is changing the alpha and creating the color tint over the image.
# List view (Recycler view in this case) for displaying data. The list view has a header added programmatically.
# Simple underlay view for providing background to the list view but not its header.
# Linear layout containing the title text that gets scaled and simple view to provide padding.

