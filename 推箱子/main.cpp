#include <stdio.h>
#include <Windows.h>
#include <conio.h>

int i, j;

int map[7][8] =
{
	{1,1,1,1,1,1,1,1},
    {1,0,0,1,0,0,0,1},
	{1,0,4,3,3,4,0,1},
	{1,5,4,3,7,0,1,1},
	{1,0,4,3,3,4,0,1},
	{1,0,0,1,0,0,0,1},
	{1,1,1,1,1,1,1,1}
};

void DrawMap();
void Color(int color);
void PlayGame();

//������
int main() 
{
	PlayGame();
}

void DrawMap()
{
	for (i = 0; i < 7; i++)
	{
		for ( j = 0; j < 8; j++)
		{
			switch (map[i][j])
			{
			case 0:
				printf("  ");
				break; 
			case 1:
				Color(12);
				printf("��");
				break;
			case 3:
				Color(11);
				printf("��");
				break;
			case 4:
				Color(9);
				printf("��");
				break;
			case 5:
				Color(10);
				printf("��");
				break;
			case 7:
				Color(13);
				printf("��");
				break;
			default:
				break;
			}
		}
		printf("\n");
	}
}

void Color(int color)
{
	HANDLE hConsole = GetStdHandle(STD_OUTPUT_HANDLE);
	SetConsoleTextAttribute(hConsole, color);
}

void PlayGame()
{
	char input;
	while (1)
	{
		//����
		system("cls");
		DrawMap();

		//ȷ���˵�λ��
		for (i = 0; i < 7; i++)
		{
			for (j = 0; j < 8; j++)
			{
				if (map[i][j] == 5)
					break;
			}
			if (map[i][j] == 5)
				break;
		}
		printf("(%d,%d)", i + 1, j + 1);

		//���ռ��̵�����
		input = _getch();

		switch (input)
		{
		case 'W':
		case 'w':
			//��ǰ���λ���ǿյػ�Ŀ�ĵ�
			if (map[i - 1][j] == 0 || map[i-1][j]==3)
			{
				map[i - 1][j] += 5;
				map[i][j] -= 5;
			}
			else if (map[i - 1][j] == 4 || map[i-1][j]==7) //��ǰ������
			{
				//���ӵ�ǰ���ǿյػ�Ŀ�ĵ�
				if (map[i - 2][j] == 0 || map[i-2][j]==3) 
				{
					map[i - 2][j] += 4;
					map[i - 1][j] += 1;

					map[i][j] -= 5;
				}
			}
			break;
		case 'S':
		case 's':
			//��ǰ���λ���ǿյػ�Ŀ�ĵ�
			if (map[i + 1][j] == 0 || map[i + 1][j] == 3)
			{
				map[i + 1][j] += 5;
				map[i][j] -= 5;
			}
			else if (map[i + 1][j] == 4 || map[i + 1][j] == 7) //��ǰ������
			{
				//���ӵ�ǰ���ǿյػ�Ŀ�ĵ�
				if (map[i + 2][j] == 0 || map[i + 2][j] == 3)
				{
					map[i + 2][j] += 4;
					map[i + 1][j] += 1;

					map[i][j] -= 5;
				}
			}
			break;
		case 'A':
		case 'a':
			//��ǰ���λ���ǿյػ�Ŀ�ĵ�
			if (map[i][j-1] == 0 || map[i][j-1] == 3)
			{
				map[i ][j-1] += 5;
				map[i][j] -= 5;
			}
			else if (map[i ][j-1] == 4 || map[i ][j-1] == 7) //��ǰ������
			{
				//���ӵ�ǰ���ǿյػ�Ŀ�ĵ�
				if (map[i ][j-2] == 0 || map[i ][j-2] == 3)
				{
					map[i ][j-2] += 4;
					map[i ][j-1] += 1;

					map[i][j] -= 5;
				}
			}
			break;
		case 'D':
		case 'd':
			//��ǰ���λ���ǿյػ�Ŀ�ĵ�
			if (map[i][j + 1] == 0 || map[i][j + 1] == 3)
			{
				map[i][j + 1] += 5;
				map[i][j] -= 5;
			}
			else if (map[i][j + 1] == 4 || map[i][j + 1] == 7) //��ǰ������
			{
				//���ӵ�ǰ���ǿյػ�Ŀ�ĵ�
				if (map[i][j + 2] == 0 || map[i][j + 2] == 3)
				{
					map[i][j + 2] += 4;
					map[i][j + 1] += 1;

					map[i][j] -= 5;
				}
			}
			break;
		default:
			break;
		}
	}
}