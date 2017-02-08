/*
 * Copyright (c) 2002-2017 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.kernel.api.schema_new;

import java.util.Arrays;

import org.neo4j.kernel.api.TokenNameLookup;

import static java.lang.String.format;

public class LabelSchemaDescriptor implements SchemaDescriptor
{
    private final int labelId;
    private final int[] propertyIds;

    LabelSchemaDescriptor( int labelId, int... propertyIds )
    {
        this.labelId = labelId;
        this.propertyIds = propertyIds;
    }

    @Override
    public <R> R computeWith( SchemaComputer<R> processor )
    {
        return processor.computeSpecific( this );
    }

    @Override
    public void processWith( SchemaProcessor processor )
    {
        processor.processSpecific( this );
    }

    @Override
    public String userDescription( TokenNameLookup tokenNameLookup )
    {
        return String.format( ":%s(%s)", tokenNameLookup.labelGetName( labelId ),
                SchemaUtil.niceProperties( tokenNameLookup, propertyIds ) );
    }

    public int getLabelId()
    {
        return labelId;
    }

    public int[] getPropertyIds()
    {
        return propertyIds; // TODO: use Iterable or similar?
    }

    @Override
    public boolean equals( Object o )
    {
        if ( o != null && o instanceof LabelSchemaDescriptor )
        {
            LabelSchemaDescriptor that = (LabelSchemaDescriptor)o;
            return labelId == that.getLabelId() && Arrays.equals( propertyIds, that.getPropertyIds() );
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return Arrays.hashCode( propertyIds ) + 31 * labelId;
    }
}