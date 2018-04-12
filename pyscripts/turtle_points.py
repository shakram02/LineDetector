import turtle

turtle.setup(800, 600)
turtle.penup()

def get_mouse_click_coor(x, y):
    print("Point(",x,",", y,")")
    turtle.goto(x, y)
    turtle.dot()


turtle.onscreenclick(get_mouse_click_coor)
turtle.mainloop()