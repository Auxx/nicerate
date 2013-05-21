NiceRate
========

This is a small library to deal with one small problem - asking users for a rating after using app for some time. Rater.isClosable() should be called in app's main activity in onBackPressed() method. It will return true if activity should be closed or false otherwise. False means user was presented with rate asking dialog and needs to make his decision.

This dialog will be shown only once during application lifetime.