public class Driver {
	public static void main(String[] args) {
		System.out.println("▣AccountTest");
		ServerTest();
		System.out.println("------------");
		System.out.println("▣MusicTest");
		MusicTest();
		System.out.println("------------");
	}
	public static void ServerTest()
	{
		Account account=new Account();
		account.setID("testID");
		System.out.println(account.getID());
		account.setPW("testPW");
		System.out.println(account.getPW());
		int[] testGenre = new int[9];
		testGenre[0] = 3;
		testGenre[1] = 1;
		testGenre[2] = 4;
		testGenre[3] = 1;
		testGenre[4] = 5;
		testGenre[5] = 9;
		testGenre[6] = 2;
		testGenre[7] = 6;
		testGenre[8] = 5;
		account.setGenre(testGenre);
		for(int i=0;i<9;i++) {
			System.out.print(account.getGenre()[i] + " ");
		}
		System.out.println("");
	}
	public static void MusicTest()
	{
		Music music = new Music();
		music.setTitle("testTitle");
		System.out.println(music.getTitle());
		music.setURL("testURL");
		System.out.println(music.getURL());
		music.setThumbnailURL("testThumbnailURL");
		System.out.println(music.getThumbnailURL());
		music.setMsg("testMsg");
		System.out.println(music.getMsg());
		music.likeCount();
		System.out.println(music.getLike());
		music.setLike(314);
		System.out.println(music.getLike());
		music.setRecommender("testRecommender");
		System.out.println(music.getRecommender());
		music.setGenre(314);
		System.out.println(music.getGenre());
	}
}
