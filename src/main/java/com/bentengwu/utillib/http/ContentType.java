package com.bentengwu.utillib.http;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang.StringUtils;

/**
 * @Title: ContentType.java 
 * 
 * @author email: <a href="bentengwu@163.com">徐伟宏</a> 
 * @date 2013-5-27 下午04:21:34 
 * @version :
 * @Description: 
 */

public class ContentType {
	public static final String JSON = "application/json";
	public static final String XML ="application/xml";
	public static final String IMAGE="image/jpeg";
	public static final String OCTET_STREAM = "application/octet-stream";
	public static final String HTML ="text/html";

	/**
	 *@description  根据文档的后缀名获取content-type
	 *@author thender email: bentengwu@163.com
	 *@date 2019/7/4 19:32
	 *@param miniType	
	 *@return java.lang.String
	 **/
	public static final String findContentType(final String miniType) {
		String _miniType = miniType;
		if (!StringUtils.startsWith(_miniType, ".")) {
			_miniType = "." + _miniType;
		}
		String contentType = contentTypeJson.get(_miniType).getAsString();
		if (StringUtils.isNotBlank(contentType)) {
			return contentType.trim();
		}else{
			return null;
		}
	}

	public static void main(String[] args) {
//		System.out.println(findContentType(".png"));
//		JsonObject contentTypeJson = new JsonParser().parse(contentTypeJsonStr).getAsJsonObject();
//		System.out.println(contentTypeJson.get(".png").getAsString());
	}



	public  static  String contentTypeJsonStr = "{\".*\":\"application/octet-stream\",\n" +
			"\".001\":\"application/x-001\",\n" +
			"\".323\":\"text/h323\",\n" +
			"\".907\":\"drawing/907\",\n" +
			"\".acp\":\"audio/x-mei-aac\",\n" +
			"\".aif\":\"audio/aiff\",\n" +
			"\".aiff\":\"audio/aiff\",\n" +
			"\".asa\":\"text/asa\",\n" +
			"\".asp\":\"text/asp\",\n" +
			"\".au\":\"audio/basic\",\n" +
			"\".awf\":\"application/vnd.adobe.workflow\",\n" +
			"\".bmp\":\"application/x-bmp\",\n" +
			"\".c4t\":\"application/x-c4t\",\n" +
			"\".cal\":\"application/x-cals\",\n" +
			"\".cdf\":\"application/x-netcdf\",\n" +
			"\".cel\":\"application/x-cel\",\n" +
			"\".cg4\":\"application/x-g4\",\n" +
			"\".cit\":\"application/x-cit\",\n" +
			"\".cml\":\"text/xml\",\n" +
			"\".cmx\":\"application/x-cmx\",\n" +
			"\".crl\":\"application/pkix-crl\",\n" +
			"\".csi\":\"application/x-csi\",\n" +
			"\".cut\":\"application/x-cut\",\n" +
			"\".dbm\":\"application/x-dbm\",\n" +
			"\".dcd\":\"text/xml\",\n" +
			"\".der\":\"application/x-x509-ca-cert\",\n" +
			"\".dib\":\"application/x-dib\",\n" +
			"\".doc\":\"application/msword\",\n" +
			"\".drw\":\"application/x-drw\",\n" +
			"\".dwg\":\"application/x-dwg\",\n" +
			"\".dxf\":\"application/x-dxf\",\n" +
			"\".emf\":\"application/x-emf\",\n" +
			"\".ent\":\"text/xml\",\n" +
			"\".etd\":\"application/x-ebx\",\n" +
			"\".fax\":\"image/fax\",\n" +
			"\".fif\":\"application/fractals\",\n" +
			"\".frm\":\"application/x-frm\",\n" +
			"\".gbr\":\"application/x-gbr\",\n" +
			"\".gif\":\"image/gif\",\n" +
			"\".gp4\":\"application/x-gp4\",\n" +
			"\".hmr\":\"application/x-hmr\",\n" +
			"\".hpl\":\"application/x-hpl\",\n" +
			"\".hrf\":\"application/x-hrf\",\n" +
			"\".htc\":\"text/x-component\",\n" +
			"\".html\":\"text/html\",\n" +
			"\".htx\":\"text/html\",\n" +
			"\".iff\":\"application/x-iff\",\n" +
			"\".igs\":\"application/x-igs\",\n" +
			"\".img\":\"application/x-img\",\n" +
			"\".isp\":\"application/x-internet-signup\",\n" +
			"\".java\":\"java/*\",\n" +
			"\".jpeg\":\"image/jpeg\",\n" +
			"\".jsp\":\"text/html\",\n" +
			"\".lar\":\"application/x-laplayer-reg\",\n" +
			"\".lavs\":\"audio/x-liquid-secure\",\n" +
			"\".lmsff\":\"audio/x-la-lms\",\n" +
			"\".ltr\":\"application/x-ltr\",\n" +
			"\".m2v\":\"video/x-mpeg\",\n" +
			"\".m4e\":\"video/mpeg4\",\n" +
			"\".man\":\"application/x-troff-man\",\n" +
			"\".mfp\":\"application/x-shockwave-flash\",\n" +
			"\".mhtml\":\"message/rfc822\",\n" +
			"\".mid\":\"audio/mid\",\n" +
			"\".mil\":\"application/x-mil\",\n" +
			"\".mnd\":\"audio/x-musicnet-download\",\n" +
			"\".mocha\":\"application/x-javascript\",\n" +
			"\".mp1\":\"audio/mp1\",\n" +
			"\".mp2v\":\"video/mpeg\",\n" +
			"\".mp4\":\"video/mpeg4\",\n" +
			"\".mpd\":\"application/vnd.ms-project\",\n" +
			"\".mpeg\":\"video/mpg\",\n" +
			"\".mpga\":\"audio/rn-mpeg\",\n" +
			"\".mps\":\"video/x-mpeg\",\n" +
			"\".mpv\":\"video/mpg\",\n" +
			"\".mpw\":\"application/vnd.ms-project\",\n" +
			"\".mtx\":\"text/xml\",\n" +
			"\".net\":\"image/pnetvue\",\n" +
			"\".nws\":\"message/rfc822\",\n" +
			"\".out\":\"application/x-out\",\n" +
			"\".p12\":\"application/x-pkcs12\",\n" +
			"\".p7c\":\"application/pkcs7-mime\",\n" +
			"\".p7r\":\"application/x-pkcs7-certreqresp\",\n" +
			"\".pc5\":\"application/x-pc5\",\n" +
			"\".pcl\":\"application/x-pcl\",\n" +
			"\".pdf\":\"application/pdf\",\n" +
			"\".pdx\":\"application/vnd.adobe.pdx\",\n" +
			"\".pgl\":\"application/x-pgl\",\n" +
			"\".pko\":\"application/vnd.ms-pki.pko\",\n" +
			"\".plg\":\"text/html\",\n" +
			"\".plt\":\"application/x-plt\",\n" +
			"\".ppa\":\"application/vnd.ms-powerpoint\",\n" +
			"\".pps\":\"application/vnd.ms-powerpoint\",\n" +
			"\".prf\":\"application/pics-rules\",\n" +
			"\".prt\":\"application/x-prt\",\n" +
			"\".pwz\":\"application/vnd.ms-powerpoint\",\n" +
			"\".ra\":\"audio/vnd.rn-realaudio\",\n" +
			"\".ras\":\"application/x-ras\",\n" +
			"\".rdf\":\"text/xml\",\n" +
			"\".red\":\"application/x-red\",\n" +
			"\".rjs\":\"application/vnd.rn-realsystem-rjs\",\n" +
			"\".rlc\":\"application/x-rlc\",\n" +
			"\".rm\":\"application/vnd.rn-realmedia\",\n" +
			"\".rmi\":\"audio/mid\",\n" +
			"\".rmm\":\"audio/x-pn-realaudio\",\n" +
			"\".rms\":\"application/vnd.rn-realmedia-secure\",\n" +
			"\".rmx\":\"application/vnd.rn-realsystem-rmx\",\n" +
			"\".rp\":\"image/vnd.rn-realpix\",\n" +
			"\".rsml\":\"application/vnd.rn-rsml\",\n" +
			"\".rv\":\"video/vnd.rn-realvideo\",\n" +
			"\".sat\":\"application/x-sat\",\n" +
			"\".sdw\":\"application/x-sdw\",\n" +
			"\".slb\":\"application/x-slb\",\n" +
			"\".slk\":\"drawing/x-slk\",\n" +
			"\".smil\":\"application/smil\",\n" +
			"\".snd\":\"audio/basic\",\n" +
			"\".sor\":\"text/plain\",\n" +
			"\".spl\":\"application/futuresplash\",\n" +
			"\".ssm\":\"application/streamingmedia\",\n" +
			"\".stl\":\"application/vnd.ms-pki.stl\",\n" +
			"\".sty\":\"application/x-sty\",\n" +
			"\".swf\":\"application/x-shockwave-flash\",\n" +
			"\".tg4\":\"application/x-tg4\",\n" +
			"\".tif\":\"image/tiff\",\n" +
			"\".tiff\":\"image/tiff\",\n" +
			"\".top\":\"drawing/x-top\",\n" +
			"\".tsd\":\"text/xml\",\n" +
			"\".uin\":\"application/x-icq\",\n" +
			"\".vcf\":\"text/x-vcard\",\n" +
			"\".vdx\":\"application/vnd.visio\",\n" +
			"\".vpg\":\"application/x-vpeg005\",\n" +
			"\".vsw\":\"application/vnd.visio\",\n" +
			"\".vtx\":\"application/vnd.visio\",\n" +
			"\".wav\":\"audio/wav\",\n" +
			"\".wb1\":\"application/x-wb1\",\n" +
			"\".wb3\":\"application/x-wb3\",\n" +
			"\".wiz\":\"application/msword\",\n" +
			"\".wk4\":\"application/x-wk4\",\n" +
			"\".wks\":\"application/x-wks\",\n" +
			"\".wma\":\"audio/x-ms-wma\",\n" +
			"\".wmf\":\"application/x-wmf\",\n" +
			"\".wmv\":\"video/x-ms-wmv\",\n" +
			"\".wmz\":\"application/x-ms-wmz\",\n" +
			"\".wpd\":\"application/x-wpd\",\n" +
			"\".wpl\":\"application/vnd.ms-wpl\",\n" +
			"\".wr1\":\"application/x-wr1\",\n" +
			"\".wrk\":\"application/x-wrk\",\n" +
			"\".ws2\":\"application/x-ws\",\n" +
			"\".wsdl\":\"text/xml\",\n" +
			"\".xdp\":\"application/vnd.adobe.xdp\",\n" +
			"\".xfd\":\"application/vnd.adobe.xfd\",\n" +
			"\".xhtml\":\"text/html\",\n" +
			"\".xml\":\"text/xml\",\n" +
			"\".xq\":\"text/xml\",\n" +
			"\".xquery\":\"text/xml\",\n" +
			"\".xsl\":\"text/xml\",\n" +
			"\".xwd\":\"application/x-xwd\",\n" +
			"\".sis\":\"application/vnd.symbian.install\",\n" +
			"\".x_t\":\"application/x-x_t\",\n" +
			"\".apk\":\"application/vnd.android.package-archive\",\n" +
			"\".301\":\"application/x-301\",\n" +
			"\".906\":\"application/x-906\",\n" +
			"\".a11\":\"application/x-a11\",\n" +
			"\".aifc\":\"audio/aiff\",\n" +
			"\".anv\":\"application/x-anv\",\n" +
			"\".asf\":\"video/x-ms-asf\",\n" +
			"\".asx\":\"video/x-ms-asf\",\n" +
			"\".avi\":\"video/avi\",\n" +
			"\".biz\":\"text/xml\",\n" +
			"\".bot\":\"application/x-bot\",\n" +
			"\".c90\":\"application/x-c90\",\n" +
			"\".cat\":\"application/vnd.ms-pki.seccat\",\n" +
			"\".cdr\":\"application/x-cdr\",\n" +
			"\".cer\":\"application/x-x509-ca-cert\",\n" +
			"\".cgm\":\"application/x-cgm\",\n" +
			"\".class\":\"java/*\",\n" +
			"\".cmp\":\"application/x-cmp\",\n" +
			"\".cot\":\"application/x-cot\",\n" +
			"\".crt\":\"application/x-x509-ca-cert\",\n" +
			"\".css\":\"text/css\",\n" +
			"\".dbf\":\"application/x-dbf\",\n" +
			"\".dbx\":\"application/x-dbx\",\n" +
			"\".dcx\":\"application/x-dcx\",\n" +
			"\".dgn\":\"application/x-dgn\",\n" +
			"\".dll\":\"application/x-msdownload\",\n" +
			"\".dot\":\"application/msword\",\n" +
			"\".dtd\":\"text/xml\",\n" +
			"\".dxb\":\"application/x-dxb\",\n" +
			"\".edn\":\"application/vnd.adobe.edn\",\n" +
			"\".eml\":\"message/rfc822\",\n" +
			"\".epi\":\"application/x-epi\",\n" +
			"\".exe\":\"application/x-msdownload\",\n" +
			"\".fdf\":\"application/vnd.fdf\",\n" +
			"\".gl2\":\"application/x-gl2\",\n" +
			"\".hgl\":\"application/x-hgl\",\n" +
			"\".hpg\":\"application/x-hpgl\",\n" +
			"\".hqx\":\"application/mac-binhex40\",\n" +
			"\".hta\":\"application/hta\",\n" +
			"\".htm\":\"text/html\",\n" +
			"\".htt\":\"text/webviewhtml\",\n" +
			"\".icb\":\"application/x-icb\",\n" +
			"\".ig4\":\"application/x-g4\",\n" +
			"\".iii\":\"application/x-iphone\",\n" +
			"\".ins\":\"application/x-internet-signup\",\n" +
			"\".IVF\":\"video/x-ivf\",\n" +
			"\".jfif\":\"image/jpeg\",\n" +
			"\".la1\":\"audio/x-liquid-file\",\n" +
			"\".latex\":\"application/x-latex\",\n" +
			"\".lbm\":\"application/x-lbm\",\n" +
			"\".m1v\":\"video/x-mpeg\",\n" +
			"\".m3u\":\"audio/mpegurl\",\n" +
			"\".mac\":\"application/x-mac\",\n" +
			"\".math\":\"text/xml\",\n" +
			"\".mht\":\"message/rfc822\",\n" +
			"\".midi\":\"audio/mid\",\n" +
			"\".mml\":\"text/xml\",\n" +
			"\".mns\":\"audio/x-musicnet-stream\",\n" +
			"\".movie\":\"video/x-sgi-movie\",\n" +
			"\".mp2\":\"audio/mp2\",\n" +
			"\".mp3\":\"audio/mp3\",\n" +
			"\".mpa\":\"video/x-mpg\",\n" +
			"\".mpe\":\"video/x-mpeg\",\n" +
			"\".mpg\":\"video/mpg\",\n" +
			"\".mpp\":\"application/vnd.ms-project\",\n" +
			"\".mpt\":\"application/vnd.ms-project\",\n" +
			"\".mpv2\":\"video/mpeg\",\n" +
			"\".mpx\":\"application/vnd.ms-project\",\n" +
			"\".mxp\":\"application/x-mmxp\",\n" +
			"\".nrf\":\"application/x-nrf\",\n" +
			"\".odc\":\"text/x-ms-odc\",\n" +
			"\".p10\":\"application/pkcs10\",\n" +
			"\".p7b\":\"application/x-pkcs7-certificates\",\n" +
			"\".p7m\":\"application/pkcs7-mime\",\n" +
			"\".p7s\":\"application/pkcs7-signature\",\n" +
			"\".pci\":\"application/x-pci\",\n" +
			"\".pcx\":\"application/x-pcx\",\n" +
			"\".pdf\":\"application/pdf\",\n" +
			"\".pfx\":\"application/x-pkcs12\",\n" +
			"\".pic\":\"application/x-pic\",\n" +
			"\".pls\":\"audio/scpls\",\n" +
			"\".pot\":\"application/vnd.ms-powerpoint\",\n" +
			"\".ppm\":\"application/x-ppm\",\n" +
			"\".prn\":\"application/x-prn\",\n" +
			"\".ptn\":\"application/x-ptn\",\n" +
			"\".r3t\":\"text/vnd.rn-realtext3d\",\n" +
			"\".ram\":\"audio/x-pn-realaudio\",\n" +
			"\".rat\":\"application/rat-file\",\n" +
			"\".rec\":\"application/vnd.rn-recording\",\n" +
			"\".rgb\":\"application/x-rgb\",\n" +
			"\".rjt\":\"application/vnd.rn-realsystem-rjt\",\n" +
			"\".rle\":\"application/x-rle\",\n" +
			"\".rmf\":\"application/vnd.adobe.rmf\",\n" +
			"\".rmj\":\"application/vnd.rn-realsystem-rmj\",\n" +
			"\".rmp\":\"application/vnd.rn-rn_music_package\",\n" +
			"\".rmvb\":\"application/vnd.rn-realmedia-vbr\",\n" +
			"\".rnx\":\"application/vnd.rn-realplayer\",\n" +
			"\".rpm\":\"audio/x-pn-realaudio-plugin\",\n" +
			"\".sam\":\"application/x-sam\",\n" +
			"\".sdp\":\"application/sdp\",\n" +
			"\".sit\":\"application/x-stuffit\",\n" +
			"\".sld\":\"application/x-sld\",\n" +
			"\".smi\":\"application/smil\",\n" +
			"\".smk\":\"application/x-smk\",\n" +
			"\".sol\":\"text/plain\",\n" +
			"\".spc\":\"application/x-pkcs7-certificates\",\n" +
			"\".spp\":\"text/xml\",\n" +
			"\".sst\":\"application/vnd.ms-pki.certstore\",\n" +
			"\".stm\":\"text/html\",\n" +
			"\".svg\":\"text/xml\",\n" +
			"\".tdf\":\"application/x-tdf\",\n" +
			"\".tga\":\"application/x-tga\",\n" +
			"\".tld\":\"text/xml\",\n" +
			"\".rt\":\"text/vnd.rn-realtext\",\n" +
			"\".pr\":\"application/x-pr\",\n" +
			"\".mi\":\"application/x-mi\",\n" +
			"\".js\":\"application/x-javascript\",\n" +
			"\".g4\":\"application/x-g4\",\n" +
			"\".ai\":\"application/postscript\",\n" +
			"\".torrent\":\"application/x-bittorrent\",\n" +
			"\".txt\":\"text/plain\",\n" +
			"\".uls\":\"text/iuls\",\n" +
			"\".vda\":\"application/x-vda\",\n" +
			"\".vml\":\"text/xml\",\n" +
			"\".vss\":\"application/vnd.visio\",\n" +
			"\".vsx\":\"application/vnd.visio\",\n" +
			"\".vxml\":\"text/xml\",\n" +
			"\".wax\":\"audio/x-ms-wax\",\n" +
			"\".wb2\":\"application/x-wb2\",\n" +
			"\".wbmp\":\"image/vnd.wap.wbmp\",\n" +
			"\".wk3\":\"application/x-wk3\",\n" +
			"\".wkq\":\"application/x-wkq\",\n" +
			"\".wm\":\"\",\n" +
			"\".wmd\":\"application/x-ms-wmd\",\n" +
			"\".wml\":\"text/vnd.wap.wml\",\n" +
			"\".wmx\":\"video/x-ms-wmx\",\n" +
			"\".wp6\":\"application/x-wp6\",\n" +
			"\".wpg\":\"application/x-wpg\",\n" +
			"\".wq1\":\"application/x-wq1\",\n" +
			"\".wri\":\"application/x-wri\",\n" +
			"\".wsc\":\"text/scriptlet\",\n" +
			"\".wvx\":\"video/x-ms-wvx\",\n" +
			"\".xdr\":\"text/xml\",\n" +
			"\".xfdf\":\"application/vnd.adobe.xfdf\",\n" +
			"\".xlw\":\"application/x-xlw\",\n" +
			"\".xpl\":\"audio/scpls\",\n" +
			"\".xql\":\"text/xml\",\n" +
			"\".xsd\":\"text/xml\",\n" +
			"\".xslt\":\"text/xml\",\n" +
			"\".x_b\":\"application/x-x_b\",\n" +
			"\".sisx\":\"application/vnd.symbian.install\",\n" +
			"\".ipa\":\"application/vnd.iphone\",\n" +
			"\".xap\":\"application/x-silverlight-app\",\n" +
			"\".ws\":\"application/x-ws\",\n" +
			"\".ps\":\"application/x-ps\",\n" +
			"\".ps\":\"application/x-ps\",\n" +
			"\".pl\":\"application/x-perl\",\n" +
			"\".ls\":\"application/x-javascript\",\n" +
			"\".fo\":\"text/xml\",\n" +
			"\".xls\":\"application/x-xls,application/vnd.ms-excel\",\n" +
			"\".vst\":\"application/x-vst,application/vnd.visio\",\n" +
			"\".vsd\":\"application/x-vsd,application/vnd.visio\",\n" +
			"\".tif\":\"image/tiff\",\n" +
			"\".rtf\":\"application/x-rtf,application/msword\",\n" +
			"\".ppt\":\"application/x-ppt,application/vnd.ms-powerpoint\",\n" +
			"\".png\":\"image/png\",\n" +
			"\".mdb\":\"application/x-mdb,application/msaccess\",\n" +
			"\".jpg\":\"image/jpeg\",\n" +
			"\".jpe\":\"image/jpeg\",\n" +
			"\".ico\":\"image/x-icon\",\n" +
			"\".eps\":\"application/x-ps,application/postscript\",\n" +
			"\".dwf\":\"Model/vnd.dwf,application/x-dwf\"}";


	//mintype 对应的content-type. 相同的key存在多个content-type选项的时候用逗号分隔.
	public static final JsonObject contentTypeJson = new JsonParser().parse(contentTypeJsonStr).getAsJsonObject();

}


