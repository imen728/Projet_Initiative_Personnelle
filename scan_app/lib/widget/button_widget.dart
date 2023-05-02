import 'dart:developer';

import 'package:flutter/material.dart';

class ButtonWidget extends StatelessWidget {
  final String text;
  final VoidCallback onClicked;

  ButtonWidget(this.text, this.onClicked, {Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) => ElevatedButton(
        child: Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(
              Icons.scanner,
              color: Colors.white,
            ),
            SizedBox(
              width: 20,
            ),
            Text(
              text,
              style: TextStyle(fontSize: 24),
            ),
          ],
        ),
        style: ElevatedButton.styleFrom(
            fixedSize: Size(350, 50),
            primary: Colors.redAccent, // Set the background color
            onPrimary: Colors.white, // Set the text color
            elevation: 0, // Remove the default elevation
            shape: RoundedRectangleBorder(
              // Set the shape of the button
              borderRadius: BorderRadius.circular(12),
            ),
            textStyle: const TextStyle(
              fontSize: 20,
              fontWeight: FontWeight.w500,
            )),
        onPressed: onClicked,
      );
}
