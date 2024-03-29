import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:scan_app/page/qr_create_page.dart';
import 'package:scan_app/page/qr_scan_page.dart';
import 'package:scan_app/widget/button_widget.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();
  SystemChrome.setPreferredOrientations([
    DeviceOrientation.portraitUp,
    DeviceOrientation.portraitDown,
  ]);

  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  static final String title = 'Scan Qr Code Exam';

  @override
  Widget build(BuildContext context) => MaterialApp(
        debugShowCheckedModeBanner: false,
        title: title,
        theme: ThemeData(
          primaryColor: Colors.red,
          scaffoldBackgroundColor: Colors.black,
        ),
        home: MainPage(title: title),
      );
}

class MainPage extends StatefulWidget {
  final String title;

  const MainPage({
    required this.title,
  });

  @override
  _MainPageState createState() => _MainPageState();
}

class _MainPageState extends State<MainPage> {
  void goTO() {
    Navigator.of(context).push(MaterialPageRoute(
      builder: (BuildContext context) => QRCreatePage(),
    ));
  }

  void goTO2() {
    Navigator.of(context).push(MaterialPageRoute(
      builder: (BuildContext context) => QRScanPage(),
    ));
  }

  @override
  Widget build(BuildContext context) => Scaffold(
        appBar: AppBar(
          title: Text(widget.title),
          backgroundColor: Colors.redAccent,
        ),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              ButtonWidget('Create QR Code', goTO),
              const SizedBox(height: 32),
              ButtonWidget('Scan QR Code', goTO2),
            ],
          ),
        ),
      );
}
