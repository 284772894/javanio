## �˿���������
### windows �C�ͻ���
* ��ע���regedit HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\ Services\TCPIP\Parameters
* �½� DWORDֵ��name��TcpTimedWaitDelay��value��0��ʮ���ƣ� �C> ����Ϊ0
* �½� DWORDֵ��name��MaxUserPort��value��65534��ʮ���ƣ� �C> �������������65534 ����ϵͳ

### liunx�ͻ���
* cat /proc/sys/net/ipv4/ip_local_port_range  ֵΪ32768 61000
	* ���Ҳ���ǹ�61000-32768=28232���˿ڿ���ʹ�ã�����IP����ֻ�ܷ���28232��TCP�����Թ���Ա��ݣ��Ѷ˿ڵķ�Χ�����������
	* echo "1024 65535"> /proc/sys/net/ipv4/ip_local_port_range  ������64511���˿ڿ���. 
	* ��������ֻ����ʱ��ϵͳ�´��������ỹԭ�� ��Ϊ���׵��������޸�/etc/sysctl.conf�ļ�������һ������ net.ipv4.ip_local_port_range= 1024 65535
* sysctl -p
	* ���ڿ���ʹ�õĶ˿ڴﵽ64510��������ϵͳ�������еķ�������û��ռ�ô���1024�Ķ˿ڵģ���Ϊ������centosϵͳ������������Ҫ��ﵽ50�����󣬻�������취��

* ����IP��ַ
* һ����豾����������Ϊ eth0����ô�ֶ�����Ӽ��������IP��
* ifconfig eth0:1 192.168.190.151
* ifconfig eth0:2 192.168.190.152 ......
* ����͵��һЩ��
	* for i in `seq 1 9`; do ifconfig eth0:$i 192.168.190.15$i up ; done
	* ��Щ�����IP��ַ��һ������������ service network restart �ͻᶪʧ��
	* Ϊ��ģ���Ϊ��ʵ�������ڲ��Զˣ��ֶ��ٴ����9��vmware���������


