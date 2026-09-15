import SwiftUI
import Playgrounds
import gym_client_kt

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Self.Context) -> UIViewController {
        AppViewControllerKt.create()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Self.Context) {
    }
}

struct ContentView: View {
    var body: some View {
        TabView(selection: /*@START_MENU_TOKEN@*//*@PLACEHOLDER=Selection@*/.constant(1)/*@END_MENU_TOKEN@*/) {
            Text("Tab Content 1").tabItem {
                /*@START_MENU_TOKEN@*/Text("Tab Label 1")/*@END_MENU_TOKEN@*/
            }
            .tag(1)
            ComposeView().tabItem {
                /*@START_MENU_TOKEN@*/Text("Tab Label 2")/*@END_MENU_TOKEN@*/
            }
            .tag(2)
        }
    }
}

#Preview {
    ContentView()
}
