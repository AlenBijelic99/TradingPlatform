import React, {useState, useEffect} from 'react';
import {Accordion} from '@hilla/react-components/Accordion.js';
import {AccordionPanel} from '@hilla/react-components/AccordionPanel.js';
import {VerticalLayout} from '@hilla/react-components/VerticalLayout';
import {Button} from '@hilla/react-components/Button.js';
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
import {IntegerField, IntegerFieldChangeEvent} from "@hilla/react-components/IntegerField";
import {useAuth} from "Frontend/util/auth";
import {Notification} from '@hilla/react-components/Notification.js';
import {EndpointError} from "@hilla/frontend";

/**
 * The account view. This view is displayed when the user navigates to the /account route.
 * It displays the current user's account information such as funds, owned cryptocurrencies chart and trade history.
 * @constructor
 * @Author Bijelic Alen & Bogale Tegest
 * @Date 28.01.2024
 */
export default function AccountView() {
    const gridRef = React.useRef<any>(null);
    const [currentUser, setCurrentUser] = useState<User>();
    const [ownedCryptos, setOwnedCryptos] = useState<CryptoHoldingDto[]>();
    const [myTrades, setMyTrades] = useState<Trade[]>();
    const [userFundValue, setUserFundValue] = useState<number>(0);
    const [fundValue, setFundValue] = useState<number>(50);

    useEffect(() => {
        UserEndpoint.getAuthenticatedUser().then((user) => {
            setCurrentUser(user);
        });

        // Load the owned cryptocurrencies
        TradeService.getCryptoHoldings().then((ownedCrypto) => {
            setOwnedCryptos(ownedCrypto);
        });

    }, []);
    useEffect(() => {
        // Load the trade history for the current user
        TradeService.getTrades().then((trades) => {
            setMyTrades(trades);
        });

        // Workaround for column width recalculation
        setTimeout(() => {
            gridRef.current?.recalculateColumnWidths();
        }, 100);
    }, []);

    useEffect(() => {
        if (currentUser) {
            setUserFundValue(currentUser.funds);
        }
    }, [currentUser]);

    /**
     * Handle the fund update when the user clicks on the update button.
     *
     */
    const handleFundUpdate = () => {
        if (currentUser && currentUser.id) {
            const updatedFund = userFundValue + fundValue;
            
            // Update the fund value using the UserService
            UserService.updateFund(
                currentUser.id,
                updatedFund
            ).then(r => {
                // Display a success notification
                setCurrentUser({...currentUser, funds: updatedFund});
                setUserFundValue(updatedFund);
                Notification.show(`Successfully updated fund to ${updatedFund}`);
            }).catch((error: EndpointError) => {
                // Display an error notification
                Notification.show(`Error during fund update: ${error.message}`, {theme: 'error'});
            });
        }
    }

    /**
     * Handle the fund change when the user changes the fund value.
     * @param e the event
     */
    const handleFundChange = (e: IntegerFieldChangeEvent) => {
        setFundValue(parseInt(e.target.value));
    };

    /**
     * Calculate the current value of the cryptocurrency based on the historical trades.
     * @param symbol the symbol of the cryptocurrency
     * @param quantity the quantity of the cryptocurrency
     * @param trades the historical trades
     */
    function calculateCurrentValue(symbol: string | undefined, quantity: number, trades: Trade[] | undefined): number {
        if (!symbol || !trades) {
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
            return totalValue;
        }, 0);
        currentValue += quantity

        return currentValue;
    }

    /**
     * Render the trade type.
     * @param trade the trade to render the type for
     */
    const tradeTypeRenderer = (trade: Trade) => {
        return <span>
        {trade?.type ? trade?.type === TradeType.BUY ? "BUY" : "SELL" : "-"}
    </span>

    };
    /**
     * Render the trade symbol. (e.g. BTC for Bitcoin)
     * @param trade the trade to render the symbol for
     */
    const tradeSymboleRenderer = (trade: Trade) => {
        return <span>{trade?.cryptoCurrency?.symbol || '-'}</span>;

    };
    /**
     * Render the trade price.
     * @param trade the trade to render the price for
     */
    const tradePriceRenderer = (trade: Trade) => {
        return <span>{trade?.price || '-'}</span>;
    };

    /**
     * Render the trade date.
     * @param trade the trade to render the date for
     */
    const tradeDateRenderer = (trade: Trade) => {
        return <span>{trade?.date || '-'}</span>;
    };

    return (
        <Accordion>
            <AccordionPanel summary="Manage Fund">
                <VerticalLayout>
                    <span> Fund : {userFundValue}</span>
                    <IntegerField
                        label="Fund"
                        helperText="Add/withdrow"
                        min={0}
                        max={10000000}
                        value={fundValue.toString()}
                        stepButtonsVisible
                        onChange={(e) => handleFundChange(e)}
                    />
                    <Button onClick={handleFundUpdate}>Update Fund</Button>
                </VerticalLayout>
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
                            // Get the current exchange rate or price for the cryptocurrency in USD
                            const cryptoPriceInUSD = ownedCrypto.cryptoCurrency?.lastPrice || 0;
                            // Calculate the current value in USD based on historical trades
                            const currentValueUSD = calculateCurrentValue(symbol, quantity, myTrades) * cryptoPriceInUSD;
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
