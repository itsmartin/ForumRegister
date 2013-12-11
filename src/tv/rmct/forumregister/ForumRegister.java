package tv.rmct.forumregister;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.BooleanPrompt;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;


public class ForumRegister extends JavaPlugin implements Listener {
	
	private static final Pattern EMAIL_REGEX = Pattern.compile("(?:(?:\\r\\n)?[ \\t])*(?:(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*)|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*:(?:(?:\\r\\n)?[ \\t])*(?:(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*)(?:,\\s*(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*))*)?;\\s*)");
	private static final String CONSOLE_INVOCATION_MESSAGE = ChatColor.RED + "That command is not available from the console";
	private static final String CONFIRM_EMAIL_MESSAGE = ChatColor.YELLOW + "Please type your email address again (on its own) to confirm:";
	private static final String EMAILS_DIFFER_MESSAGE = ChatColor.RED + "Your response did not match. Please start again.";
	private static final String REGISTER_SYNTAX_HELP_MESSAGE = ChatColor.YELLOW + "To register, type " + ChatColor.AQUA + "/register "
			+ ChatColor.ITALIC + "your-email-address";
	private static final String RESET_SYNTAX_HELP_MESSAGE = ChatColor.YELLOW + "To reset your password, type " + ChatColor.AQUA + "/reset";
	private static final String PLAYER_JOIN_MESSAGE = null;
	private static final String REGISTRATION_BEGIN_MESSAGE = ChatColor.GRAY + "Registering you...";
	private static final String REGISTRATION_SUCCESS_MESSAGE = ChatColor.GREEN + "Forum registration succeeded! Please check your email for\nfurther instructions.";
	private static final String REGISTRATION_FAILED_MESSAGE = ChatColor.RED + "Registration failed. Please try again later. If this problem persists, please contact staff.";
	private static final String ALREADY_REGISTERED_MESSAGE = ChatColor.RED + "You are already registered!\n" 
			+ ChatColor.YELLOW + "If you have forgotten your password, you can reset it by\ntyping " 
			+ ChatColor.AQUA + "/reset"; 

	private static final String CONFIRM_RESET_MESSAGE = ChatColor.YELLOW + "Are you sure you want to reset your forum password?\n"
			+ "Please say " + ChatColor.AQUA + "yes" + ChatColor.YELLOW + " or " + ChatColor.AQUA + "no"
			+ ChatColor.YELLOW + ":";
	private static final String RESET_BEGIN_MESSAGE = ChatColor.GRAY + "Resetting password...";
	private static final String RESET_SUCCESS_MESSAGE = ChatColor.GREEN + "Your password has been reset. Please check your email for\nfurther instructions.";
	private static final String RESET_FAILED_MESSAGE = ChatColor.RED + "Password reset failed. Please try again later. If this problem persists, please contact staff.\n"
			+ ChatColor.RED + "If you need further help, please contact staff.";
	private static final String RESET_ABORTED_MESSAGE = ChatColor.RED + "Password reset cancelled.";
	
	private static final String EMAIL_INVALID_MESSAGE = ChatColor.RED + "That does not appear to be a valid email address. For help\nwith registration, type " + ChatColor.AQUA + "/register" + ChatColor.RED + ".";
	
	private String url;
	private String key;
	private ConversationFactory registrationConversation = new ConversationFactory(this)
			.withFirstPrompt(new EmailConfirmationPrompt())
			.withLocalEcho(false);
	private ConversationFactory resetConversation = new ConversationFactory(this)
	.withFirstPrompt(new ResetConfirmationPrompt())
	.withLocalEcho(false);

	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		this.saveDefaultConfig();
		this.key = this.getConfig().getString("key");
		this.url = this.getConfig().getString("url");
		
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onJoin(PlayerJoinEvent e) {
		if (PLAYER_JOIN_MESSAGE != null)
			e.getPlayer().sendMessage(PLAYER_JOIN_MESSAGE);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("register")) {
			if (sender instanceof Player) {
				cmdRegister((Player) sender, args);
				return true;
			} else {
				sender.sendMessage(CONSOLE_INVOCATION_MESSAGE);
				return true;
			}
		}
		if (command.getName().equalsIgnoreCase("reset")) {
			if (sender instanceof Player) {
				cmdReset((Player) sender, args);
				return true;
			} else {
				sender.sendMessage(CONSOLE_INVOCATION_MESSAGE);
				return true;
			}
		}
		return false;
	}
	
	private void cmdRegister(Player player, String[] args) {
		if (player.isConversing()) return;
		if (args.length == 1) {
			if (EMAIL_REGEX.matcher(args[0]).matches()) {
				Conversation c = registrationConversation.buildConversation(player);
				c.getContext().setSessionData("email", args[0]);
				c.getContext().setSessionData("player", player);
				c.begin();
			} else {
				player.sendMessage(EMAIL_INVALID_MESSAGE);
			}
		} else {
			player.sendMessage(REGISTER_SYNTAX_HELP_MESSAGE);
		}
	}
	
	private void cmdReset(Player player, String[] args) {
		if (player.isConversing()) return;
		if (args.length > 1) {
			player.sendMessage(RESET_SYNTAX_HELP_MESSAGE);
			return;
		}
		Conversation c = resetConversation.buildConversation(player);
		c.getContext().setSessionData("player", player);
		c.begin();
	}
	
	private void initRegistration(final Player player, final String email) {
		final String ign = player.getName();
		getServer().getScheduler().runTaskLaterAsynchronously(this, new Runnable() {
			public void run() {
				syncSendMessage(player, REGISTRATION_BEGIN_MESSAGE);
				RegistrationOutcome outcome = sendRegistration(ign, email, false);
				if (outcome == RegistrationOutcome.SUCCESS) {
					syncSendMessage(player, REGISTRATION_SUCCESS_MESSAGE);
				} else {
					if (outcome == RegistrationOutcome.ALREADY_REGISTERED) {
						syncSendMessage(player, ALREADY_REGISTERED_MESSAGE);
					} else {
						syncSendMessage(player, REGISTRATION_FAILED_MESSAGE);
					}
				}
			}
		}, 0);
	}
	

	private void initReset(final Player player) {
		final String ign = player.getName();
		getServer().getScheduler().runTaskLaterAsynchronously(this, new Runnable() {
			public void run() {
				syncSendMessage(player, RESET_BEGIN_MESSAGE);
				RegistrationOutcome outcome = sendRegistration(ign, ign, true);
				if (outcome == RegistrationOutcome.SUCCESS) {
					syncSendMessage(player, RESET_SUCCESS_MESSAGE);
				} else {
					syncSendMessage(player, RESET_FAILED_MESSAGE);
				}
			}
		}, 0);
	}
	
	private void syncSendMessage(final Player player, final String message) {
		getServer().getScheduler().runTask(this, new Runnable() {
			public void run() {
				player.sendMessage(message);
			}
		});
	}
		
	private RegistrationOutcome sendRegistration(String ign, String email, boolean resetPassword) {

			try {
				URL url = new URL(this.url);
				String function = (resetPassword ? "reset" : "register");

				getLogger().info((resetPassword ? "Resetting password for " : "Registering ") + ign + " (" + email + ")");
				
				String postData = URLEncoder.encode("key", "UTF-8") + "=" + URLEncoder.encode(this.key, "UTF-8") + "&" +
						URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(ign, "UTF-8") + "&" +
						URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
						URLEncoder.encode("function", "UTF-8") + "=" + URLEncoder.encode(function, "UTF-8");

				URLConnection connection = url.openConnection();
				connection.setDoOutput(true);

				OutputStream outputStream = connection.getOutputStream();
				outputStream.write(postData.getBytes("UTF-8"));
				outputStream.close();

				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

				String response = br.readLine();
				if (response.toLowerCase().startsWith("success")) {
					getLogger().info("Operation succeded");
					return RegistrationOutcome.SUCCESS;
				} else if (response.toLowerCase().startsWith("error:already registered")) {
					getLogger().info("Failed - already registered");
					return RegistrationOutcome.ALREADY_REGISTERED;
				} else {
					getLogger().info("Failed with response: " + response);
					return RegistrationOutcome.OTHER_ERROR;
				}
				
			} catch (MalformedURLException e) {
				getLogger().severe("Malformed URL in configuration");
				return RegistrationOutcome.CONFIGURATION_ERROR;
			} catch (UnsupportedEncodingException e) {
				getLogger().severe("UTF-8 encoding is not supported");
				return RegistrationOutcome.CONFIGURATION_ERROR;
			} catch (IOException e) {
				getLogger().info("Failed due to I/O error: " + e.getMessage());
				return RegistrationOutcome.CONNECTION_ERROR;
			}


	}
	
	
	private class EmailConfirmationPrompt extends StringPrompt {		

		@Override
		public String getPromptText(ConversationContext context) { 
			String prompt = ChatColor.YELLOW + "You are registering for the forum as:\n    "
					+ ChatColor.AQUA + ((Player) context.getSessionData("player")).getName() + "\n"
					+ ChatColor.YELLOW + "Your registration email will be sent to:\n    "
					+ ChatColor.AQUA + ((String) context.getSessionData("email")) + "\n"
					+ CONFIRM_EMAIL_MESSAGE;
			return prompt;
		}

		@Override
		public Prompt acceptInput(ConversationContext context, String input) {
			if (((String) context.getSessionData("email")).equalsIgnoreCase(input)) {
				// It matches; begin registration
				((ForumRegister) context.getPlugin()).initRegistration((Player) context.getSessionData("player"), input);
			} else {
				// It didn't match, say so
				context.getForWhom().sendRawMessage(EMAILS_DIFFER_MESSAGE);
			}
			return Prompt.END_OF_CONVERSATION;
		}

	}
	
	private class ResetConfirmationPrompt extends BooleanPrompt {

		@Override
		public String getPromptText(ConversationContext context) {
			return CONFIRM_RESET_MESSAGE;
		}

		@Override
		protected Prompt acceptValidatedInput(ConversationContext context, boolean response) {
			if (response)
				((ForumRegister) context.getPlugin()).initReset((Player) context.getSessionData("player"));
			else
				context.getForWhom().sendRawMessage(RESET_ABORTED_MESSAGE);

			return Prompt.END_OF_CONVERSATION;
		}
		
	}
}
