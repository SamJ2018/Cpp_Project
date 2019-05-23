#include "snake.h"
#include<windows.h>

void gotoxy1(HANDLE hOut1, int x, int y)
{
	COORD pos;
	pos.X = x;             //������
	pos.Y = y;            //������
	SetConsoleCursorPosition(hOut1, pos);
}
HANDLE hOut1 = GetStdHandle(STD_OUTPUT_HANDLE);//������ʾ���������

Snake::Snake(Wall &tempWall,Food &tempFood):wall(tempWall),food(tempFood)
{
	pHead = NULL;
	isLoop = false;
}

void Snake::initSnake()
{
	destroyPoint();
	addPoint(5, 3);
	addPoint(5, 4);
	addPoint(5, 5);
}

//�������нڵ�
void Snake::destroyPoint()
{
	Point* pCur = pHead;

	while (pHead != NULL)
	{
		pCur = pHead->next;
		delete pHead;

		pHead = pCur;
	}
}

void Snake::addPoint(int x, int y)
{
	//�����½ڵ�
	Point* newPoint = new Point;
	newPoint->x = x;
	newPoint->y = y;
	newPoint->next = NULL;

	//���ԭ��ͷ��Ϊ�գ���Ϊ����
	if (pHead != NULL)
	{
		wall.setWall(pHead->x, pHead->y, '=');
		gotoxy1(hOut1, pHead->y * 2, pHead->x);
		cout << "=";
	}

	newPoint->next = pHead;
	pHead = newPoint;//������ͷ
	wall.setWall(pHead->x, pHead->y, '@');
	gotoxy1(hOut1, pHead->y * 2, pHead->x);
	cout << "@";
}

void Snake::delPoint()
{
	//�����ڵ����ϣ���ȥ��ɾ������

	//Ϊʲôд||�������
	if (pHead == NULL)
	{
		return;
	}
	if (pHead->next == NULL)
	{
		return;
	}

	Point* pCur = pHead->next;
	Point* pPre = pHead;

	while (pCur->next != NULL)
	{
		pPre = pPre->next;
		pCur = pCur->next;
	}

	//ɾ��β�ڵ�
	wall.setWall(pCur->x, pCur->y, ' ');
	gotoxy1(hOut1, pCur->y * 2, pCur->x);
	cout << " ";
	delete pCur;
	//��ֹҰָ��
	pCur = NULL;
	pPre->next = NULL;

}

bool Snake::move(char key)
{
	int x = pHead->x;
	int y = pHead->y;

	switch (key)
	{
	case UP:
		x--; break;
	case DOWN:
		x++; break;
	case LEFT:
		y--; break;
	case RIGHT:
		y++;
	default:
		break;
	}

	/*�ж������һ������β�ͣ���Ӧ������*/
	Point* pCur = pHead->next;
	Point* pPre = pHead;

	while (pCur->next != NULL)
	{
		pPre = pPre->next;
		pCur = pCur->next;
	}
	if (pCur->x == x && pCur->y == y)
	{
		//������β�ͣ�ѭ��
		isLoop = true;
	}
	else
	{
		//�ж��û�����λ���Ƿ�ɹ�
		if (wall.getWall(x, y) == '*' || wall.getWall(x, y) == '=')
		{
			addPoint(x, y);
			delPoint();
			system("cls");
			wall.drawWall();
			cout << "�÷�Ϊ��" << getSorce() << "����" << endl;
			cout << "GAME OVER" << endl;
			return false;
		}
	}

	
	//�ƶ��ɹ����Ե�ʳ����û�Ե�ʳ��
	if (wall.getWall(x, y) == '#')
	{
		addPoint(x, y);

		//��������ʳ��
		food.setFood();
	}
	else
	{
		addPoint(x, y);
		delPoint();
		if (isLoop == true)
		{
			wall.setWall(x, y, '@');
			gotoxy1(hOut1, y * 2, x);
			cout << "@";
		}
	}
	return true;
}

int Snake::getSleepTime()
{
	int sleepTime = 0;

	int size = countList();
	if (size < 5)
	{
		sleepTime = 300;
	}
	else if (size >= 5 && size < 15)
	{
		sleepTime = 200;
	}
	else if (size >= 15 && size < 25)
	{
		sleepTime = 100;
	}
	else
	{
		sleepTime = 50;
	}

	return sleepTime;
}

int Snake::countList()
{
	int size = 0;
	Point* curPoint = pHead;
	while (curPoint != NULL)
	{
		size++;
		curPoint = curPoint->next;
	}
	return size;
}

int Snake::getSorce()
{
	int size = countList();
	int score = (size-3) * 100;
	return score;
}
