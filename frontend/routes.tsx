import {protectRoutes} from '@hilla/react-auth';
import HomeView from 'Frontend/views/home/HomeView';
import LoginView from 'Frontend/views/login/LoginView.js';
import MainLayout from 'Frontend/views/MainLayout.js';
import {lazy} from 'react';
import {createBrowserRouter, RouteObject, useNavigate} from 'react-router-dom';
import RegisterView from "Frontend/views/signUp/RegisterView";
import WelcomeView from "Frontend/views/WelcomeView";

const TradingView = lazy(async () => import('Frontend/views/trading/tradingView'));

export const routes = protectRoutes([
    {path: '/', element: <WelcomeView/>},
    {path: '/register', element: <RegisterView/>},
    {path: '/login', element: <LoginView/>},
    {
        element: <MainLayout/>,
        handle: {title: 'Main'},
        children: [
            {path: '/home', element: <HomeView/>, handle: {title: 'Home', requiresLogin: true}},
            {path: '/trade', element: <TradingView/>, handle: {title: 'Trade', requiresLogin: true}},
        ],
    },


]) as RouteObject[];

export default createBrowserRouter(routes);
