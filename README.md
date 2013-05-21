# NiceRate

NiceRate is a library to ask app users for ratings in non-intrusive way when users quit application. This library assumes that asking users for rating is OK if such dialog appers only once and only after user used application for some time already.

## Usage

Every activity finishes its lifecycle when user taps Back button. When Back button is tapped, `onBackPressed()` method is called. Default method just calls `finish()`. To use NiceRate you should override `onBackPressed()` and call `Rater.isClosable()` static method which returns `TRUE` if activity should be close or `FALSE` otherwise. `FALSE` is returned when user is presented with rating dialog and needs to make a decision, so closing activity is not desired.

Simplest example:

```java
@Override
public void onBackPressed() {
	if(Rater.isClosable(this)) {
		finish();
	}
}
```

To make this code really useful it should be put into your main activity, not into your every activity.

`Rater.isClosable()` may also be called with other parameters to control different aspects of Rater like dialog title and message, call counter limit etc. Check `Rater.Settings` class for available settings.

## Call counter

`Rater` will show rate dialog only once and only after certain amount of calls (app quits). The amount of calls needed to show dialog is controlled with `Rater.Settings.rateTrigger` and is set to 5 by default. That means that by default user will quit your app five times and will see rate dialog on sixth.

Call counter is stored in `SharedPreferences`, preferences name and keys are set in `Rater.Settings`. Check the source for defaults.

## Code examples

The library comes with example project which covers common use cases.

## Binary JAR

Binary JAR will be uploaded later, please use source to build your own JAR at the moment.
