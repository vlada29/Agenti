# WebSocket Example
Some examples of how to work with WebSockets.

## Simple Example

This most basic example just shows how to keep sessions in the backend so the
server can send messages later.

When the client opens the connection, the WebSocketEndpoint adds the session to
a list. Periodically, the MessageScheduler sends messages to all opened
sessions, while each client sends messages to the server.

## Example 1

In this example there is just one WebSocket endpoint in the server, but the
connection URL can be different for each client. The endpoint can route messages
based on the URL used by the client.

There is a REST endpoint so clients can send messages to specific clients.

## Example 2

Now the clients send websocket messages to other clients. The WebSocket server
endpoint can get information from the connection using a
`ServerEndpointConfig.Configurator`. In this example, it gets the username from
a cookie.

The client receives a list of connected users, which are show in the user list.
The user can click on a username and start sending information to that user.

## Example 3

In this example, the server endpoint uses a simple tag system, so each session
is mapped to a list of tags, and also each tag is mapped to a list of sessions.
When the user send a message to a specific tag, the endpoint routes the message
to all sessions with that tag.