document.querySelector("#form").addEventListener("submit", async (event) => {
  event.preventDefault();
  const input = document.querySelector("#form input");
  const userText = input.value.trim();

  if (userText === "") return;

  const userMessageDiv = document.createElement("div");
  userMessageDiv.classList.add("user-message");
  userMessageDiv.textContent = userText;
  document.getElementById("chat-box").appendChild(userMessageDiv);

  input.value = "";

  try {
    const response = await fetch("/api/chat", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ prompt: userText }),
    });

    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }

    const data = await response.text(); // Adjust based on the response type
    console.log("Response data:", data); // Debugging

    const botMessageDiv = document.createElement("div");
    botMessageDiv.classList.add("bot-message");
    botMessageDiv.textContent = data; // Adjust based on the response structure
    document.getElementById("chat-box").appendChild(botMessageDiv);
  } catch (error) {
    console.error("Error:", error);
  }

  document.getElementById("chat-box").scrollTop =
    document.getElementById("chat-box").scrollHeight;
});
