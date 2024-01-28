import React, {useState, useEffect} from 'react';
import {Accordion} from '@hilla/react-components/Accordion.js';
import {AccordionPanel} from '@hilla/react-components/AccordionPanel.js';
import {VerticalLayout} from '@hilla/react-components/VerticalLayout';
import {TextField} from '@hilla/react-components/TextField';
import {Button} from '@hilla/react-components/Button.js';
import {useForm} from '@hilla/react-form';
import UserModel from 'Frontend/generated/ch/heigvd/application/data/entities/UserModel';
import {TradeService, UserEndpoint, UserService} from 'Frontend/generated/endpoints';
import User from 'Frontend/generated/ch/heigvd/application/data/entities/User';
import {ChartSeries} from "@hilla/react-components/ChartSeries";
import {Chart} from "@hilla/react-components/Chart";
import {Avatar} from "@hilla/react-components/Avatar";
import {Grid} from "@hilla/react-components/Grid";
import {GridColumn} from "@hilla/react-components/GridColumn";
import Trade from "Frontend/generated/ch/heigvd/application/data/entities/Trade";
import TradeType from "Frontend/generated/ch/heigvd/application/data/entities/TradeType";
import CryptoHoldingDto from "Frontend/generated/ch/heigvd/application/data/dto/CryptoHoldingDto";


const tradeTypeRenderer = (trade: Trade) => {
    return <span>
        {trade?.type ? trade?.type === TradeType.BUY ? "BUY" : "SELL" : "-"}
    </span>

};
const tradeSymboleRenderer = (trade: Trade) => {
    return <span>{trade?.cryptoCurrency?.symbol || '-'}</span>;

};
const tradePriceRenderer = (trade: Trade) => {
    return <span>{trade?.price || '-'}</span>;
};

const tradeDateRenderer = (trade: Trade) => {
    return <span>{trade?.date || '-'}</span>;
};
export default function AccountView() {
    const gridRef = React.useRef<any>(null);
    let [currentUser, setCurrentUser] = useState<User>();
    const [editMode, setEditMode] = useState(false);
    const {field, model, submit} = useForm(UserModel, {onSubmit}); // Define a form using the useForm hook
    const [ownedCryptos, setOwnedCryptos] = useState<CryptoHoldingDto[]>();
    const [myTrades, setMyTrades] = useState<Trade[]>();


    useEffect(() => {
        UserEndpoint.getAuthenticatedUser().then((user) => {
            setCurrentUser(user);
        });

        // Fetch cryptocurrencies data from the backend for the current user
        TradeService.getCryptoHoldings().then((ownedCrypto) => {
            setOwnedCryptos(ownedCrypto);
        });

    }, []);
    useEffect(() => {
        TradeService.getTrades().then((trades) => {
            setMyTrades(trades);
        });

        // Workaround for column width recalculation
        setTimeout(() => {
            gridRef.current?.recalculateColumnWidths();
        }, 100);
    }, []);


    async function onSubmit(updatedUser: User) {
        try {
            // Submit the updated user profile to the backend
            //TODO : fix this. The user is not updated in the backend, not allowed error
            await UserService.update(updatedUser);
            setCurrentUser(updatedUser);
            setEditMode(false); // Exit edit mode after successful update
        } catch (error) {
            console.error('Error during update:', error);
        }
    }
    function calculateCurrentValue(symbol: string | undefined,quantity: number,  trades: Trade[] | undefined): number {
        if (!symbol || !trades) {
            //TODO remove at the end
            console.error('Symbol or trades not defined');
            return 0;
        }

        // Filter trades for the given symbol
        const symbolTrades = trades.filter((trade) => trade.cryptoCurrency?.symbol === symbol);

        // Calculate the current value based on buy and sell trades
        let currentValue = symbolTrades.reduce((totalValue, trade) => {
            if (trade.type === TradeType.BUY) {
                totalValue += trade.price * trade.quantity;
            } else if (trade.type === TradeType.SELL) {
                totalValue -= trade.price * trade.quantity;
            }
            return totalValue  ;
        }, 0);
        currentValue += quantity

        return currentValue;
    }


    return (
        <Accordion>
            <AccordionPanel summary="Personal information">
                {editMode ? (
                    // Render form in edit mode
                    <VerticalLayout>
                        <TextField label="Username" {...field(model.username)} />
                        <TextField label="First Name" {...field(model.firstName)} />
                        <TextField label="Last Name" {...field(model.lastName)} />
                        <TextField label="Funds" {...field(model.funds)} />

                        <div className="flex gap-m">
                            <Button onClick={async () => await submit() && setEditMode(false)} theme="primary">
                                Save
                            </Button>
                            <Button onClick={() => setEditMode(false)}>Cancel</Button>
                        </div>
                    </VerticalLayout>
                ) : (
                    // Render read-only view
                    <VerticalLayout>
                        <Avatar name={`${currentUser?.firstName} ${currentUser?.lastName}`}/>
                        <span>Username: {currentUser?.username}</span>
                        <span>Firstname: {currentUser?.firstName}</span>
                        <span>Lastname: {currentUser?.lastName}</span>
                        <span>Funds: {currentUser?.funds}</span>

                        <Button onClick={() => setEditMode(true)}>Edit</Button>
                    </VerticalLayout>
                )}
            </AccordionPanel>
            <AccordionPanel summary="History of trades">
                <Grid items={myTrades} allRowsVisible ref={gridRef}>
                    <GridColumn
                        header="Trade Type" flexGrow={0} autoWidth>
                        {({item}) => tradeTypeRenderer(item)}
                    </GridColumn>
                    <GridColumn
                        header="Crypto Symbol" flexGrow={0} autoWidth>
                        {({item}) => tradeSymboleRenderer(item)}
                    </GridColumn>
                    <GridColumn path="quantity" flexGrow={0} autoWidth/>
                    <GridColumn
                        header="Trade Price" flexGrow={0} autoWidth>
                        {({item}) => tradePriceRenderer(item)}
                    </GridColumn>
                    <GridColumn
                        header="date" flexGrow={0} autoWidth>
                        {({item}) => tradeDateRenderer(item)}
                    </GridColumn>
                </Grid>
            </AccordionPanel>
            <AccordionPanel summary="Crypto chart ">
                <Chart type="pie" title="Owned Cryptos" tooltip>
                    <ChartSeries
                        title="Cryptos"
                        values={ownedCryptos?.map((ownedCrypto) => {
                            const symbol = ownedCrypto.cryptoCurrency?.symbol;
                            const quantity = ownedCrypto.quantity || 0;

                            // Get the current exchange rate or price for the cryptocurrency in USD //TODO change with usd value
                            const cryptoPriceInUSD = ownedCrypto.cryptoCurrency?.lastPrice || 10000;

                            // Calculate the current value in USD based on historical trades
                            const currentValueUSD = calculateCurrentValue(symbol, quantity, myTrades) * cryptoPriceInUSD ;

                            console.log('Chart Data:', { name: symbol, y: currentValueUSD }); // tODO remove at the end Log the data

                            return {
                                name: symbol,
                                y: currentValueUSD,
                            };
                        })}
                    />
                </Chart>

            </AccordionPanel>

        </Accordion>
    );
}
