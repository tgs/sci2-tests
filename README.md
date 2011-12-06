# Sci2 Tests

## How To Build For Development

The goal is to build everything in `src/`, and copy the resulting `jar`s, along with everything from `plugins`, to a clean installation of Sci2 which will become your *testing area*.  There may be a quicker way than what follows...

Start out with a clean workspace, maybe, since you'll be changing the target platform later.
File -> Switch Workspace -> Other... -> make a new one.

Clone the GitHub repo:

* Go to https://github.com/tgs/sci2-tests and select the kind of access you want, and copy the URL near the top
* Open the Git Repositories view in Eclipse
* Click the "Clone a git repository and add the clone to this view" button at the top of the view
  * Paste the URL you copied earlier

Import the projects:

* The projects under `src` need to be imported
* Select them all, right click, and say "Import Projects..."

Download a build of sci2:

* Probably one of the nightlies.  Sorry, they're not publicly available right now... will be soon...
* Extract two copies of it.  One will be your *target platform* used for compiling, and one will be
your *testing area* where you export things to run tests.

Set up the Target Platform:

* Open `test-platform.target` in the `org.cishell.testcollector` plugin
* At the top-right of the view, you can say "Set as Target Platform."  Do that.
* Everything will be broken, that's ok.
* Make a new Target Platform:
  * Open Window -> Preferences
  * Open Plug-in Development -> Target Platform in the tree on the left.
  * Click "Add..."
  * Choose to start with the Current Target, click Next.
  * Give your shiny new platform a name, like Fred
  * In the Locations tab of your new target, click Add...
  * Choose to add an Installation
  * For the Location, choose the copy of Sci2 that you want to use for your "target platform"; click Finish.
  * That should give you about 200 extra plug-ins.
  * Don't mess with this installation of Sci2.  You want to leave it alone so you have control over what dependencies are needed to run whatever you write.
* Click Finish on the New Target Definition dialog box
* Fred the Target Platform isn't selected yet!  In the Preferences dialog box, select Fred and click OK.
* Eclipse will rebuild everything, and everything should build without errors... hopefully.

Sorry that this sucks.  You only have to do it the first time.

## Writing Tests:
Unit tests should go in separate packages from the thing they're testing.  See http://rcpquickstart.wordpress.com/2007/06/20/unit-testing-plug-ins-with-fragments/ for a good discussion of where to put tests.

* This system works with tests that are in plug-in fragments
* It does NOT support JUnit 4-style tests (where test methods are marked with @Test)
  * But it's fine if you use the JUnit 4 *bundle* to implement JUnit 3-style tests (where tests inherit from TestCase)
* Your test bundle Symbolic Name SHOULD end in ".tests".  This enables the system to make sure that all of the ".tests" bundles loaded correctly, and aren't being silently skipped.

If you want to write tests that use OSGi services (for instance, to make sure that a bundle is available), your test bundle *cannot* be a plug-in fragment.  Instead, it needs to be a normal plug-in, with an Activator that will get you a BundleContext.  See `src/org.cishell.context.test` for an example.

## Preparing to run tests:
* Find the installation of Sci2 that you want to use as your *testing area*
* Copy everything in the plugins/ directory of the git repository to the plugins/ directory of the testing area
  * One way to find the repository's plugins directory is to open the "Git Repositories" view, find the `plugins` directory of this repository, right click -> Copy Path to Clipboard.  Then open Windows Explorer and paste it into the address bar.
* Select all of the projects you Imported from Git, and right-click and choose Export.  Export to the directory of the *installation* of the *testing area*, not the "plugins" directory.

Now, when you run that copy of Sci2, it will run any unit tests that are present in this testing area.

## Running Tests:
So you write a plug-in fragment that has tests for another plug-in.  To run it...

* Select this test fragment, right-click and choose Export...
* Export to the *installation* of Sci2 that you are using as a *testing area*.
* Run sci2 in that directory.
* Hopefully you get lots of `*.tap` files in the root directory of this installation.

If you run Sci2 with the arguments `-vmargs -Dorg.cishell.testcollector.exit=true`, it will automatically exit after running the tests.  This is useful for Continuous Integration or something---although it still uses the GUI.

The test output goes in [Test Anything Protocol](http://testanything.org/) files, which will show up in the root directory of the installation.  My advice is to ignore `*-SUITE.tap` and `*#*.tap` (anything with # in the name is just the result of one method) - just concentrate on the class-level ones.  There's also `missing-bundles.tap`, which is an attempt to let you know if any bundles that end in ".tests" may be getting skipped (because of OSGi class loading problems, probably).

[Jenkins](https://wiki.jenkins-ci.org/display/JENKINS/TAP+Plugin) and [Smolder](http://sourceforge.net/projects/smolder/) are two things that read the Test Anything Protocol.  But you can also just look at the files, they're plain text with a very straightforward format.

That was arduous!  Put it all in a Jenkins build!

## Acknowledgements

This complex tangle of glue code makes use of / includes:

* [Tap4j](http://www.tap4j.org/)
* [RCP Quickstart's bundletestcollector](http://rcpquickstart.com/2008/06/12/running-unit-tests-for-rcp-and-osgi-applications/)
* [JUnit](http://www.junit.org/)
* [EasyMock](http://easymock.org/) - included because some tests not in this repo use it
* and various dependencies of all of these.  I should just use Maven, I guess :-(