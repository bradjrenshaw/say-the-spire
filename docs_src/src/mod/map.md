# The Map
While viewing the map, the buffer system is replaced with a map viewer. This
allows you to quickly follow paths.

When playing Slay the Spire, you can view the whole map for a given act at any
time by pressing map, as well as it being automatically shown between rooms. The
map consists of rooms with curved lines indicating where you can travel to. The
location of the player is either below the map (beginning of an act) or the room
they last completed. 

The map viewer is essentially a virtual cursor. If you move over a map node
normally in the course of gameplay (that is, using the d-pad, arrow keys, or
left stick), the virtual cursor will be set to that map node.

Use right stick left and right or control left and right arrows to move between
possible choices (rooms you can move to from this node, only in the forward
direction). Use right stick up or control up arrow to follow the path forward;
this will move continuously until you hit a choice or dead end (such as a boss).
Use right stick down or control down arrow to retrace your steps.

For example if given the following 3x4 map:

||1|2|3|4|
|-----|-----------|-----------|-----------|-----------|
|2|monster|elite|unknown|monster|
|1|blank|merchant|unknown|blank|
|0|monster|blank|blank|blank|

Say you are starting below the  bottom row. You have a single choice (monster,
far bottom left) The only choice from there is merchant. There are however three
choices (monster, elite, or unknown). From the bottom room your map focus will
be on the first choice (monster). hitting forward will follow the path until it
hits a branch point, so it will read something like this. monster, merchant,
choice After that it will allow you to browse the choices as before. Note that
it reads the choice again at the start of the path to avoid confusion, this
isn't a bug. If moving backwards, the path is read in reverse as you would
expect, unless you disable this setting in the config file.
