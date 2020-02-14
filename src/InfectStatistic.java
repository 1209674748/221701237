import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.Collator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * InfectStatistic
 * TODO
 *
 * @author �Գ�������
 * @version xxx
 * @since xxx
 */

class InfectStatistic {
    public static void main(String[] args) {
    	String[] provinceName= {
    			"安徽","北京","重庆","福建","甘肃","广东",
    			"广西","贵州","海南","河北","河南","黑龙江",
    			"湖北","湖南","吉林","江苏","江西","辽宁",
    			"内蒙古","宁夏","青海","山东","山西","陕西",
    			"上海","四川","天津","西藏","新疆","云南","浙江"};
    	province [] provinceList= new province[provinceName.length+1];
    	provinceList[0] = new province("全国");
    	provinceList[0].setAppear();
    	for(int i=1;i<provinceName.length+1;i++)
    	{
    		provinceList[i] = new province(provinceName[i-1]);
    	}
        List<String> list = new ArrayList<String>();
        for(int i=0;i<args.length;i++)
        {
        	list.add(args[i]);
        }
        Command command = new Command(list);
        if(!command.isLegal())
        {
        	System.exit(0);
        }
        fileRead fileoperate = new fileRead(command.logContent,provinceList);
        if(command.dateContent.equals("all"))
        {
        	fileoperate.readLog("all");
        }
        else
        {
        	fileoperate.readLog(command.dateContent);
        }
        fileWrite wr = new fileWrite(provinceList,command.province,command.type,
        		command.provinceContent,command.typeContent,command.outContent);
        wr.writeResult();
    }
}
class fileWrite
{
	private province [] provinceList;
	private boolean isProvince;
	private List<String> provinceContent;
	private String outPath;
	private boolean isType;
	private List<String> typeContent;
	private File file;
	public fileWrite(province [] provinceList,boolean isProvice,boolean isType,
			List<String> provinceContent,List<String> typeContent,String outPath)
	{
		this.provinceList = provinceList;
		this.isProvince = isProvice;
		this.provinceContent = provinceContent;
		this.isType = isType;
		this.typeContent = typeContent;
		this.outPath = outPath;
		
       	String parentPath = outPath.substring(0,outPath.lastIndexOf("\\"));
       	File file = new File(parentPath);
       	File file2 = new File(outPath);
        if (!file.exists()) 
        {
            file.mkdirs();// 能创建多级目录
            try 
            {
				file2.createNewFile();
			} 
            catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("输出路径有误");
				System.exit(0);
			}
        }
        else
        {
        	  try 
        	  {
  				file2.createNewFile();
  			  } 
        	  catch (IOException e) 
        	  {
  				// TODO Auto-generated catch block
  				System.out.println("输出路径有误");
  				System.exit(0);
  			}
        }
	}
	public void writeResult()
	{
		if(isProvince==false&&isType==false)
		{
			for(int i=0;i<provinceList.length;i++)
	        {
	        	if(provinceList[i].getAppear())
	        	{
		        	System.out.println(provinceList[i].getName());
		        	System.out.println("感染患者 "+provinceList[i].getIp());
		        	System.out.println("疑似患者 "+provinceList[i].getSp());
		        	System.out.println("治愈 "+provinceList[i].getCure());
		        	System.out.println("死亡 "+provinceList[i].getDead());
		        	System.out.println();
	        	}
	        }
		}
		if(isProvince==true&&isType==false)
		{
			for(int i=0;i<provinceList.length;i++)
			{
				for(int n=0;n<provinceContent.size();n++)
				{
					if(provinceList[i].getName().equals(provinceContent.get(n)))
					{
						System.out.println(provinceList[i].getName());
			        	System.out.println("感染患者 "+provinceList[i].getIp());
			        	System.out.println("疑似患者 "+provinceList[i].getSp());
			        	System.out.println("治愈 "+provinceList[i].getCure());
			        	System.out.println("死亡 "+provinceList[i].getDead());
			        	System.out.println();
					}
				}
			}
		}
		if(isProvince==true&&isType==true)
		{
			for(int i=0;i<provinceList.length;i++)
			{
				for(int n=0;n<provinceContent.size();n++)
				{
					if(provinceList[i].getName().equals(provinceContent.get(n)))
					{
						System.out.println(provinceList[i].getName());
						for(int j=0;j<typeContent.size();j++)
						{
							String type = typeContent.get(j);
							if(type.equals("ip"))
							{
								System.out.println("感染患者 "+provinceList[i].getIp());
							}
							if(type.equals("sp"))
							{
								System.out.println("感染患者 "+provinceList[i].getSp());
							}
							if(type.equals("cure"))
							{
								System.out.println("治愈 "+provinceList[i].getCure());
							}
							if(type.equals("dead"))
							{
								System.out.println("死亡 "+provinceList[i].getDead());
							}
						}
						System.out.println();
					}
				}
			}
		}
		if(isProvince==false&&isType==true)
		{
			for(int i=0;i<provinceList.length;i++)
	        {
	        	if(provinceList[i].getAppear())
	        	{
	        		System.out.println(provinceList[i].getName());
					for(int j=0;j<typeContent.size();j++)
					{
						String type = typeContent.get(j);
						if(type.equals("ip"))
						{
							System.out.println("感染患者 "+provinceList[i].getIp());
						}
						if(type.equals("sp"))
						{
							System.out.println("感染患者 "+provinceList[i].getSp());
						}
						if(type.equals("cure"))
						{
							System.out.println("治愈 "+provinceList[i].getCure());
						}
						if(type.equals("dead"))
						{
							System.out.println("死亡 "+provinceList[i].getDead());
						}
					}
					System.out.println();
				}
	        }
		}
//		if(!isProvice)
//        {
//	        for(int i=0;i<provinceList.length;i++)
//	        {
//	        	if(provinceList[i].getAppear())
//	        	{
//		        	System.out.println(provinceList[i].getName());
//		        	System.out.println("感染患者 "+provinceList[i].getIp());
//		        	System.out.println("疑似患者 "+provinceList[i].getSp());
//		        	System.out.println("治愈 "+provinceList[i].getCure());
//		        	System.out.println("死亡 "+provinceList[i].getDead());
//		        	System.out.println();
//	        	}
//	        }
//        }
//        else
//        {
//	        for(int i=0;i<provinceContent.size();i++)
//	        {
//	        	provinceList[findLocal(provinceContent.get(i))].setIsInput();
//	        }
//	        for(int i=0;i<provinceList.length;i++)
//	        {
//	        	if(provinceList[i].getIsInput())
//	        	{
//	        		System.out.println(provinceList[i].getName());
//		        	System.out.println("感染患者 "+provinceList[i].getIp());
//		        	System.out.println("疑似患者 "+provinceList[i].getSp());
//		        	System.out.println("治愈 "+provinceList[i].getCure());
//		        	System.out.println("死亡 "+provinceList[i].getDead());
//		        	System.out.println();
//	        	}
//	        }
//        } 
	}
	public int findLocal(String name)
	{
		for(int i=1;i<provinceList.length;i++)
		{
			if(provinceList[i].getName().equals(name))
			{
				return i;
			}
		}
		return 0;
	}
}
class province
{
	private String name;
	private int ip;
	private int sp;
	private int cure;
	private int dead;
	private boolean appear;
	private boolean isInput;
	public province(String name)
	{
		this.name=name;
		ip=0;
		sp=0;
		cure=0;
		dead=0;
		appear=false;
		isInput=false;
	}
	public void setAppear()
	{
		appear=true;
	}
	public void setIsInput()
	{
		isInput=true;
	}
	public void addIp(int n)
	{
		ip+=n;
	}
	public void subIp(int n)
	{
		ip-=n;
	}
	public void addSp(int n)
	{
		sp+=n;
	}
	public void subSp(int n)
	{
		sp-=n;
	}
	public void addCure(int n)
	{
		cure+=n;
	}
	public void subCure(int n)
	{
		cure-=n;
	}
	public void addDead(int n)
	{
		dead+=n;
	}
	public void subDead(int n)
	{
		dead-=n;
	}
	public String getName()
	{
		return name;
	}
	public int getIp()
	{
		return ip;
	}
	public int getSp()
	{
		return sp;
	}
	public int getCure()
	{
		return cure;
	}
	public int getDead()
	{
		return dead;
	}
	public boolean getAppear()
	{
		return appear;
	}
	public boolean getIsInput()
	{
		return isInput;
	}
}
class fileRead{
	String path;
	ArrayList<String> files;
	ArrayList<String> filesName;
	File file;
	File[] tempList;
	province [] provinceList;
	String [] templates = {
			"(\\S+) 新增 感染患者 (\\d+)人","(\\S+) 新增 疑似患者 (\\d+)人","(\\S+) 感染患者 流入 (\\S+) (\\d+)人",
			"(\\S+) 疑似患者 流入 (\\S+) (\\d+)人","(\\S+) 死亡 (\\d+)人","(\\S+) 治愈 (\\d+)人",
			"(\\S+) 疑似患者 确诊感染 (\\d+)人",
			"(\\S+) 排除 疑似患者 (\\d+)人"
	};
	String maxdate;
	String mindate;
	public fileRead(String path,province [] pList)
	{
		this.path=path;
		provinceList = pList;
		files = new ArrayList<String>();
		filesName = new ArrayList<String>();
		file=new File(path);
        //file=new File(path);
		if(!file.exists())
		{
			System.out.println("日志文件路径有误!");
			System.exit(0);
		}
        tempList = file.listFiles(); 
        if(tempList==null)
        {
        	System.out.println("null");
        }
        //System.out.println(tempList.length);
        for(int i=0;i<tempList.length;i++)
        {
        	String f = tempList[i].toString();
        	files.add(f);
        	filesName.add(f.substring(f.length()-18,f.length()-8));
        	//System.out.println(filesName.get(i));
        }
        if((maxdate = findMaxdate(filesName))==null)
        {
        	System.out.println("寻找最大日期出错");
        }
        if((mindate = findMindate(filesName))==null)
        {
        	System.out.println("寻找最小日期出错");
        }
	}
	public String findMaxdate(ArrayList<String> filesName)
	{
		String date="0000-00-00";
		for(int i=0;i<filesName.size();i++)
		{
			if(filesName.get(i).compareTo(date)>0)
			{
				date=filesName.get(i);			
			}
		}
		return date;
	}
	public String findMindate(ArrayList<String> filesName)
	{
		String date="9999-99-99";
		for(int i=0;i<filesName.size();i++)
		{
			if(filesName.get(i).compareTo(date)<0)
			{
				date=filesName.get(i);				
			}
		}
		return date;
	}
	public boolean readLog(String path)
	{
		
		if(path.equals("all")) 
		{
			readLogFile(maxdate);
			return true;
		}
		else
		{
			if(path.compareTo(maxdate)>0||path.compareTo(mindate)<0)
			{
				return false;
			}
			else
			{
					readLogFile(path);
					return true;
			}		
		}
	}
	public void readLogFile(String path)
	{
		int i=0;
		while(i<filesName.size()&&path.compareTo(filesName.get(i))>=0)
		{
			try {
				FileInputStream fileInputStream = new FileInputStream(files.get(i));
				InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                //StringBuffer sb = new StringBuffer();
                String text = null;
                try {
					while((text = bufferedReader.readLine()) != null){
					    statisNum(text);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
	}
	public int findLocal(String name)
	{
		for(int i=1;i<provinceList.length;i++)
		{
			if(provinceList[i].getName().equals(name))
			{
				return i;
			}
		}
		return 0;
	}
	public void statisNum(String text)
	{
		text=text.trim();
		if(text.matches(templates[0]))
		{
			//System.out.println(123);
			String provinceName = getProvince(text,0);
			//System.out.println(text);
			//System.out.println(text.substring(text.lastIndexOf(" "),text.length()-1).trim());
			int num = getNum(text);
			//int num = Integer.parseInt(text.substring(text.lastIndexOf(" "),text.length()-1).trim());
			provinceList[findLocal(provinceName)].addIp(num);
			provinceList[findLocal(provinceName)].setAppear();
			provinceList[0].addIp(num);
		}
		if(text.matches(templates[1]))
		{
			String provinceName = getProvince(text,0);
			int num = getNum(text);
			//System.out.println(text.substring(text.lastIndexOf(" "),text.length()-1).trim());
			//int num = Integer.parseInt(text.substring(text.lastIndexOf(" "),text.length()-1).trim());
			provinceList[findLocal(provinceName)].addSp(num);
			provinceList[findLocal(provinceName)].setAppear();
			provinceList[0].addSp(num);
		}
		if(text.matches(templates[2]))
		{
			//System.out.println(text);
			String provinceName1 = getProvince(text,0);
			String provinceName2 = getProvince(text,3);
			int num = getNum(text);
			//int num = Integer.parseInt(text.substring(text.lastIndexOf(" "),text.length()-1).trim());
			//System.out.println(provinceName1);
			//System.out.println(provinceName2);
			//System.out.println(num);
			provinceList[findLocal(provinceName1)].subIp(num);
			provinceList[findLocal(provinceName1)].setAppear();
			
			provinceList[findLocal(provinceName2)].addIp(num);
			provinceList[findLocal(provinceName2)].setAppear();
		}
		if(text.matches(templates[3]))
		{
			//System.out.println(text);
			String provinceName1 = getProvince(text,0);
			String provinceName2 = getProvince(text,3);
			int num = getNum(text);
			//int num = Integer.parseInt(text.substring(text.lastIndexOf(" "),text.length()-1).trim());
			//System.out.println(provinceName1);
			//System.out.println(provinceName2);
			//System.out.println(num);
			provinceList[findLocal(provinceName1)].subSp(num);
			provinceList[findLocal(provinceName1)].setAppear();
			
			provinceList[findLocal(provinceName2)].addSp(num);
			provinceList[findLocal(provinceName2)].setAppear();
			
		}
		if(text.matches(templates[4]))
		{
			//System.out.println(text);
			String provinceName = getProvince(text,0);
			int num = getNum(text);
			//int num = Integer.parseInt(text.substring(text.lastIndexOf(" "),text.length()-1).trim());
			//System.out.println(provinceName);
			//System.out.println(num);
			int i = findLocal(provinceName);
			provinceList[i].addDead(num);
			provinceList[i].subIp(num);
			provinceList[i].setAppear();
			provinceList[0].subIp(num);
			provinceList[0].addDead(num);
		}
		if(text.matches(templates[5]))
		{
			//System.out.println(text);
			String provinceName = getProvince(text,0);
			int num = getNum(text);
			//int num = Integer.parseInt(text.substring(text.lastIndexOf(" "),text.length()-1).trim());
			//System.out.println(provinceName);
			//System.out.println(num);
			int i = findLocal(provinceName);
			provinceList[i].addCure(num);
			provinceList[i].subIp(num);
			provinceList[i].setAppear();
			provinceList[0].subIp(num);
			provinceList[0].addCure(num);
		}
		if(text.matches(templates[6]))
		{
			//System.out.println(text);
			String provinceName = getProvince(text,0);
			int num = getNum(text);
			//int num = Integer.parseInt(text.substring(text.lastIndexOf(" "),text.length()-1).trim());
			//System.out.println(provinceName);
			//System.out.println(num);
			int i = findLocal(provinceName);
			provinceList[i].addIp(num);
			provinceList[i].subSp(num);
			provinceList[i].setAppear();
			provinceList[0].addIp(num);
			provinceList[0].subSp(num);
		}
		if(text.matches(templates[7]))
		{
			//System.out.println(text);
			String provinceName = getProvince(text,0);
			int num = getNum(text);
			//int num = Integer.parseInt(text.substring(text.lastIndexOf(" "),text.length()-1).trim());
			//System.out.println(provinceName);
			//System.out.println(num);
			int i = findLocal(provinceName);
			provinceList[i].subSp(num);
			provinceList[i].setAppear();
			provinceList[0].subSp(num);
		}
	}
	public String getProvince(String text,int index)
	{
		String [] temp =text.split(" ");
		return temp[index];
	}
	public Integer getNum(String text)
	{
		String regex = "\\d+";
		Pattern pattern =  Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		matcher.find();
		Integer num = Integer.parseInt(matcher.group());
		return num;
	}
}
class Command{
	Boolean list=false;
	Boolean log=false;
	Boolean out=false;
	Boolean date=false;
	Boolean type=false;
	Boolean province=false;
	String dateContent;
	String logContent;
	String outContent;
	List<String> provinceContent = new ArrayList<String>();
	List<String> typeContent = new ArrayList<String>();
	List<String> command = new ArrayList<String>();
	public Command(List<String> list)
	{
		command=list;
		dateContent="all";
		for(int i=0;i<list.size();i++)
		{
			String str = list.get(i);
			switch (str) {
				case "list":
					this.list=true;
					break;
				case "-log":
					this.log=true;
					Get_logContent(i+1);
					break;
				case "-out":
					this.out=true;
					Get_outContent(i+1);
					break;
				case "-date":
					this.date=true;
					Get_dateContent(i+1);
					break;
				case "-province":
					this.province=true;
					Get_provinceContent(i+1);
					break;
				case "-type":
					this.type=true;
					Get_typeContent(i+1);
					break;
					
			}
		}
	}
	public boolean isLegal()
	{
		if(date)
		{
			String regex = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
	        Pattern pattern = Pattern.compile(regex);
	        Matcher m = pattern.matcher(dateContent);
	        boolean dateFlag = m.matches();
	        if (!dateFlag) {
	            System.out.println("日期格式错误");
	            return false;//日期格式错误 例如:2020-1-3(正确为2020-01-03）
	        }
	        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
	        formatter.setLenient(false);
	        try{  
	            formatter.parse(dateContent);  
	        }catch(Exception e){
	            System.out.println("日期格式错误！");
	            return false;//日期格式错误 例如:2a20-01-03(出现非数字字符)
	        }
		}
		if(log)
		{
			if(logContent==null)
			{
				System.out.println("-log命名参数缺失！");
				return false;//输入-log命令 却未输入参数
			}
		}
		else
		{
			System.out.println("缺失-log命令！");
			return false;//缺少-log命令
		}
		if(out)
		{
			if(logContent==null)
			{
				System.out.println("-out命令参数缺失！");
				return false;//输入-out命令 却未输入参数
			}
		}
		else
		{
			System.out.println("缺失-out命令！");
			return false;//缺失-out命令
		}
		if(type)
		{
			if(typeContent==null)
			{
				System.out.println("-typy命令参数缺失！");
				return false;//输入-type 命令 却不输入参数
			}
			else
			{
				for(int i=0;i<typeContent.size();i++)
				{
					if(!typeContent.get(i).equals("sp")&&!typeContent.get(i).equals("ip")
							&&!typeContent.get(i).equals("cure")&&!typeContent.get(i).equals("dead"))
					{
						System.out.println("-type命令参数错误！");
						return false;//type的参数不是（ip sp cure dead四种之一）
					}
				}
			}
		}
		if(province)
		{
			if(provinceContent==null)
			{
				return false;//输入 -province命令却不输入参数
			}
		}
		return true;
	}
	public void Get_provinceContent(int i)
	{
		while(i<command.size()&&!command.get(i).matches("-.*"))
		{
			provinceContent.add(command.get(i));
			i++;
		}
//		for(int n=0;n<provinceContent.size();n++)
//		{
//			System.out.println(provinceContent.get(n));
//		}
	}
	public void Get_typeContent(int i)
	{
		while(i<command.size()&&!command.get(i).matches("-.*"))
		{
			typeContent.add(command.get(i));
			i++;
		}
//		for(int n=0;n<typeContent.size();n++)
//		{
//			System.out.println(typeContent.get(n));
//		}
	}
	public void Get_dateContent(int i)
	{
		while(i<command.size()&&!command.get(i).matches("-.*"))
		{
			dateContent=command.get(i);
			i++;
		}
//		System.out.println(dateContent);
	}
	public void Get_logContent(int i)
	{
		while(i<command.size()&&!command.get(i).matches("-.*"))
		{
			logContent=command.get(i);
			i++;
		}
//		System.out.println(logContent);
	}
	public void Get_outContent(int i)
	{
		while(i<command.size()&&!command.get(i).matches("-.*"))
		{
			outContent=command.get(i);
			i++;
		}
//		System.out.println(outContent);
	}
}
